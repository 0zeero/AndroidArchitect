# Architectural Decision: Centralized Core (The Industry Standard)

This document formalizes our decision to adopt a **Centralized Core Architecture** for our Android application. This approach ensures that shared infrastructure, consistent branding, and common utilities are managed in a single, well-defined location.

---

## 1. Executive Summary

We have chosen the **Centralized Core** approach to balance **modular independence** with **global consistency**. In this architecture, `:core` modules provide the "engine" and "identity" of the app, while `:feature` modules provide the specific business logic and user experiences.

---

## 2. Why Centralized Core?

The "Industry Standard" isn't chosen just because it's popular; it's chosen because it solves the most common scaling problems in Android development.

### A. Global Consistency (Theming & UI)
In a decentralized approach, every feature might define its own "Primary Blue" or "Success Green."
*   **Centralized Solution**: We use a `:core:ui` module where symbols like `AppTheme`, `AppColors`, and custom `DesignSystem` components reside.
*   **Benefit**: Updating the brand's primary color takes seconds and reflects across every screen in the app instantly.

### B. Unified Observability (Logging & Analytics)
Tracking down bugs is impossible if "Feature A" logs to Firebase and "Feature B" logs to Timber with different formats.
*   **Centralized Solution**: `:core:network` provides a pre-configured `OkHttpClient` with a global `HttpLoggingInterceptor` and an `AnalyticsInterceptor`.
*   **Benefit**: All network traffic, regardless of which feature initiated it, is logged and tracked consistently, making debugging a unified experience.

### C. Resource Efficiency (Connection Pooling)
Android devices have limited resources.
*   **Centralized Solution**: All features share a single `OkHttpClient` instance managed by Dagger/HILT in `:core`.
*   **Benefit**: Features share a single connection pool, reducing memory overhead and improving performance by reusing existing sockets.

---

## 3. Pros and Cons

| Feature | Centralized Core (Our Choice) | Impact |
| :--- | :--- | :--- |
| **Consistency** | **Superior.** Shared themes, logs, and auth. | Reduced UI/UX debt. |
| **DRY Principle** | **High.** Write once in Core, use everywhere. | Faster development of new features. |
| **Build Speed** | **Fast.** Decoupled features allow parallel builds. | Better developer productivity. |
| **Testing** | **Robust.** Core can be tested independently. | Fewer regressions in infrastructure code. |
| **Onboarding** | **Clear.** New developers look to Core for "how-to." | Quicker ramp-up for new team members. |
| **Cons: Bottleneck** | Changing Core triggers re-builds of dependents. | Mitigated by keeping Core lean and stable. |
| **Cons: Complexity** | Requires deeper understanding of DI (Dagger/Hilt). | Initial learning curve for the team. |

---

## 4. Architectural Visualization (Mermaid)

The following diagram illustrates how Feature modules depend on the Core infrastructure while remaining isolated from each other.

```mermaid
graph TD
    %% Define Modules
    subgraph ":app Module"
        App[Main Application<br/>(Wiring & Navigation)]
    end

    subgraph ":features (Business Logic)"
        F1[feature:home]
        F2[feature:profile]
        F3[feature:settings]
    end

    subgraph ":core (Infrastructure)"
        CoreUI[core:ui<br/>(Themes, Fonts, Design System)]
        CoreNet[core:network<br/>(Retrofit, Logging, Auth)]
        CoreData[core:database<br/>(Shared Preferences, Room)]
        CoreUtil[core:utils<br/>(Common Extensions)]
    end

    %% Dependencies
    App --> F1
    App --> F2
    App --> F3
    
    F1 --> CoreUI
    F1 --> CoreNet
    F1 --> CoreUtil

    F2 --> CoreUI
    F2 --> CoreNet
    F2 --> CoreData

    F3 --> CoreUI
    F3 --> CoreUtil
    
    %% Implicit Core relations
    CoreNet -.->|Logs to| CoreUtil
```

---

## 5. Implementation Guidelines

To ensure this approach remains successful as the team grows, we follow these rules:

1.  **Core is Engine, Not Content**: Do NOT put feature-specific code in `:core`. If it's only used by "Home," it belongs in `:feature:home`.
2.  **API vs Implementation**: `:core` should expose clean APIs (interfaces). This allows us to swap an implementation (e.g., switching from Firebase to Posthog) without touching any code in the feature modules.
3.  **Strict Linting**: We use Gradle dependency rules to ensure features never depend on other features (Feature-to-Feature dependency is a forbidden pattern).

---

## 6. Future Proofing

If the team decides to change a core library (e.g., moving from `Moshi` to `KotlinX Serialization`), the migration is centralized in `:core:network`. Feature modules won't even notice the change if the Core API remains stable.

> **Decision Date**: 2026-01-15
> **Status**: Approved & Implementing
