# 🚀 DevNexus — Enterprise AI-Driven SRE & Incident Management Platform

<p align="center">
  <img src="https://img.shields.io/badge/Next.js-16.2-black?style=for-the-badge&logo=next.js" alt="Next.js" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.3-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Google_Gemini-2.0_Flash-orange?style=for-the-badge&logo=google" alt="Google Gemini AI" />
  <img src="https://img.shields.io/badge/TypeScript-5.0-blue?style=for-the-badge&logo=typescript" alt="TypeScript" />
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License" />
</p>

**DevNexus** is a full-stack, enterprise-grade Site Reliability Engineering (SRE) and Incident Management platform. It combines real-time error ingestion, role-based triage workflows, automated GitHub Actions CI/CD webhook handling, Java Spring Boot SLA engines, and **Google Gemini 2.0 Flash AI** to automatically diagnose root causes and generate step-by-step code fixes for application crashes.

---

## 🌟 Key Features

- 🤖 **Automated AI Root-Cause Engine**: Powered by Google Gemini 2.0 Flash to analyze error stack traces, assign risk scores, and recommend step-by-step code fixes.
- 👥 **Role-Based Operations Portals**:
  - **Admin Console (`/dashboard/admin`)**: Organization setup, project API key generation, team creation, and audit logging.
  - **Manager Operations Center (`/dashboard/manager`)**: Project telemetry overview, SLA deadline tracking, and developer assignment routing.
  - **Developer Triage Workspace (`/dashboard/developer`)**: Personal incident queue, status progression (`OPEN` ➔ `ASSIGNED` ➔ `IN_PROGRESS` ➔ `RESOLVED`), and resolution history.
- ⚡ **Multi-Language Telemetry Ingestion (`/api/ingest`)**: Real-time crash report collection for Java, Node.js, React, Python, Go, and mobile applications with rate-limiting and deduplication.
- 🔗 **GitHub Webhooks & CI/CD Tracking**: Cryptographically signed (`HMAC-SHA256`) GitHub webhooks that automatically create CRITICAL SLA incidents when GitHub Actions workflows (`workflow_run`) fail.
- ☕ **Java 21 Spring Boot SLA Engine**: Real-time SLA response/resolution deadline monitoring, background monitors, and OpenAPI 3.0 / Swagger UI documentation (`http://localhost:8080/swagger-ui.html`).
- 📧 **Gmail SMTP Notification Pipeline**: Automated verification emails, organization invites, and SLA escalation notifications.

---

## 🏗️ Architecture & Monorepo Structure

```text
DevNexus/
├── incident-management-system/    # Next.js 16 App Router Frontend & Node API Services
│   ├── src/
│   │   ├── app/                    # Web pages, API endpoints, role dashboard routes
│   │   ├── components/             # React Tailwind components (StatCards, Sidebars, Modals)
│   │   ├── hooks/                  # Custom React hooks (CSRF, notifications, network status)
│   │   ├── lib/                    # Gemini AI service, JWT auth, database client, mailer
│   │   └── services/               # Issue, Project, Organization, and Notification services
│   ├── prisma/                     # Schema & migrations (@DevNexus/prisma-client)
│   ├── scripts/                    # Database seed scripts & test tools
│   ├── tests/                      # Unit & integration tests (Bun test runner)
│   └── e2e/                        # End-to-end tests (Playwright)
│
├── devnexus-backend/               # Java 21 Spring Boot 3 Engine & REST Controllers
│   ├── src/main/java/com/devnexus/
│   │   ├── config/                 # Security, CORS, Redis, Swagger, Async configuration
│   │   ├── controller/             # Auth, Issue, Project, Webhook, Health controllers
│   │   ├── dto/                    # Data Transfer Objects & API response wrappers
│   │   ├── entity/                 # JPA database entities & Enums
│   │   ├── exception/              # Custom exception handlers
│   │   ├── repository/             # Spring Data JPA repositories
│   │   ├── security/               # JWT filter, token provider, user details service
│   │   ├── service/                # SLA calculation rules, AI integration, Auth services
│   │   └── util/                   # Shared utility classes
│   └── src/main/resources/         # application.yml configuration
│
└── devnexus-java-sdk/              # Java & Spring Boot Client SDK for error tracking
    └── src/main/java/              # Auto-capture via @ControllerAdvice, manual exception API
```

---

## 🔑 Pre-Seeded Test Credentials

After running the database seed script, you can log in with the following default accounts at `http://localhost:3000/auth/login`:

| Role | Email | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@devnexus.io` | `Password123!` | Full Organization & System Access |
| **Manager** | `manager@devnexus.io` | `Password123!` | Project Operations & Developer Routing |
| **Developer** | `dev@devnexus.io` | `Password123!` | Personal Queue & Incident Resolution Workspace |

---

## ⚡ Quick-Start Guide

### Prerequisites

- **Node.js**: v18.0.0 or higher
- **Java JDK**: 21 or higher
- **Maven**: 3.8 or higher
- **PostgreSQL**: Local or cloud database running on port `5432`

---

### 1. Database Setup

Create a PostgreSQL database named `DevNexus`:

```sql
CREATE DATABASE DevNexus;
CREATE USER DevNexus WITH PASSWORD 'devnexus_secret';
GRANT ALL PRIVILEGES ON DATABASE DevNexus TO DevNexus;
```

---

### 2. Setting Environment Variables

Create `.env` inside `incident-management-system/` **before running the app**:

```env
DATABASE_URL="postgresql://DevNexus:devnexus_secret@localhost:5432/DevNexus"
JWT_SECRET="<generate with: openssl rand -hex 32>"
GEMINI_API_KEY="YOUR_GEMINI_API_KEY"
GITHUB_WEBHOOK_SECRET="your_webhook_secret"
NEXT_PUBLIC_APP_URL="http://localhost:3000"
GMAIL_USER="your_email@gmail.com"
GMAIL_APP_PASSWORD="your_gmail_app_password"
```

> **Important:** `JWT_SECRET` is **required** — the app will refuse to start without it. Generate a secure secret with `openssl rand -hex 32`.

---

### 3. Frontend & Next.js Server

```powershell
cd incident-management-system

# Install dependencies
npm install

# Push Prisma schema to database
npx prisma db push

# Seed initial organization and users
npx bun run scripts/seed-db.ts

# Start Next.js development server
npm run dev
```

The web application will start at **`http://localhost:3000`**.

---

### 4. Java Spring Boot Engine

```powershell
cd devnexus-backend

# Set JWT_SECRET environment variable (must match the one in .env above)
$env:JWT_SECRET="<same secret from step 2>"

# Run Spring Boot application
mvn spring-boot:run
```

The Java engine will start at **`http://localhost:8080`**.
Interactive Swagger documentation is available at **`http://localhost:8080/swagger-ui.html`**.

---

## 📦 Client SDK Integrations

DevNexus provides native SDKs for both **Node.js / React** and **Java / Spring Boot** applications.

### ☕ 1. Java & Spring Boot SDK (`devnexus-java-sdk`)

Add `devnexus-java-sdk` to your Spring Boot project's `pom.xml`:

```xml
<dependency>
    <groupId>com.devnexus</groupId>
    <artifactId>devnexus-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

Add configuration to your `application.yml`:

```yaml
DevNexus:
  sdk:
    api-key: devnexus_sk_demo_key_12345
    base-url: http://localhost:3000/api/ingest
    enabled: true
```

*The SDK automatically registers `@ControllerAdvice` to intercept unhandled Spring Boot controller exceptions, send stack traces to DevNexus, and trigger Gemini AI analysis!*

#### Manual Exception Capture (Java):
```java
DevNexusClient client = new DevNexusClient("devnexus_sk_demo_key_12345", "http://localhost:3000/api/ingest");
client.captureException(throwable, "CRITICAL", Map.of("component", "OrderService"));
```

---

### 🌐 2. Node.js, React & TypeScript SDK (`@DevNexus/sdk`)

In any Node.js, React, or TypeScript project, install the SDK:

```bash
npm install devnexus-sdk
```

Initialize at app startup:

```typescript
import { DevNexus, IssueSeverity } from 'devnexus-sdk';

DevNexus.init({
  apiKey: 'devnexus_sk_demo_key_12345',
  baseUrl: 'http://localhost:3000/api/ingest',
});

// Capture manual exception
try {
  processPayment();
} catch (error) {
  DevNexus.captureException(error, {
    severity: IssueSeverity.HIGH,
    tags: { component: 'checkout' }
  });
}
```

---

## 📄 License

Distributed under the MIT License.
