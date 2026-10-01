import { DevNexus, IssueSeverity } from '../../sdk';

async function runTest() {
  DevNexus.init({
    apiKey: 'devnexus_sk_demo_key_12345',
    baseUrl: 'http://localhost:3000/api/ingest',
    flushInterval: 1000,
  });

  const uniqueId = Date.now();
  console.log(`âš¡ [Client App] App started (Run ID: ${uniqueId})...`);

  // Simulating an unhandled Payment Gateway Timeout bug
  try {
    throw new Error(`Payment Gateway Timeout after 30000ms at CheckoutWorker.processPayment (Ref #${uniqueId})`);
  } catch (crashError: any) {
    console.log("ðŸ’¥ CRASH OCCURRED! Capturing automatically via DevNexus SDK...");

    await DevNexus.captureException(crashError, {
      severity: IssueSeverity.CRITICAL,
      tags: {
        component: "CheckoutWorker",
        environment: "PRODUCTION",
        runId: String(uniqueId)
      }
    });

    console.log("â³ Flushing report queue to server...");
    await DevNexus.flush();

    console.log("âœ… Crash reported and flushed to DevNexus! Refresh your Admin Dashboard now.");
  }
}

runTest().catch(console.error);
