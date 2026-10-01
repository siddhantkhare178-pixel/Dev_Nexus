import { PrismaClient } from '@DevNexus/prisma-client';
import bcrypt from 'bcryptjs';

const prisma = new PrismaClient();

async function main() {
  console.log('ðŸŒ± Seeding DevNexus database...');

  // 1. Create Organization
  const org = await prisma.organization.upsert({
    where: { id: 'org_default_DevNexus' },
    update: {},
    create: {
      id: 'org_default_DevNexus',
      name: 'DevNexus Enterprise',
      plan: 'ENTERPRISE',
    },
  });
  console.log('âœ… Created Organization:', org.name);

  const hashedPassword = await bcrypt.hash('Password123!', 10);

  // 2. Create Admin
  const admin = await prisma.user.upsert({
    where: { email: 'admin@devnexus.io' },
    update: {
      passwordHash: hashedPassword,
      status: 'ACTIVE',
      role: 'ADMIN',
    },
    create: {
      email: 'admin@devnexus.io',
      name: 'DevNexus Admin',
      passwordHash: hashedPassword,
      role: 'ADMIN',
      status: 'ACTIVE',
      emailVerifiedAt: new Date(),
      orgId: org.id,
    },
  });
  console.log('âœ… Created Admin user:', admin.email);

  // 3. Create Manager
  const manager = await prisma.user.upsert({
    where: { email: 'manager@devnexus.io' },
    update: {
      passwordHash: hashedPassword,
      status: 'ACTIVE',
      role: 'MANAGER',
    },
    create: {
      email: 'manager@devnexus.io',
      name: 'DevNexus Manager',
      passwordHash: hashedPassword,
      role: 'MANAGER',
      status: 'ACTIVE',
      emailVerifiedAt: new Date(),
      orgId: org.id,
    },
  });
  console.log('âœ… Created Manager user:', manager.email);

  // 4. Create Developer
  const dev = await prisma.user.upsert({
    where: { email: 'dev@devnexus.io' },
    update: {
      passwordHash: hashedPassword,
      status: 'ACTIVE',
      role: 'DEVELOPER',
    },
    create: {
      email: 'dev@devnexus.io',
      name: 'DevNexus Developer',
      passwordHash: hashedPassword,
      role: 'DEVELOPER',
      status: 'ACTIVE',
      emailVerifiedAt: new Date(),
      orgId: org.id,
    },
  });
  console.log('âœ… Created Developer user:', dev.email);

  // 5. Create Default Project
  const project = await prisma.project.upsert({
    where: { id: 'proj_default_DevNexus' },
    update: {},
    create: {
      id: 'proj_default_DevNexus',
      name: 'Payment Service Core',
      description: 'Core microservice for handling global user transactions and checkout flows.',
      sdkApiKey: 'devnexus_sk_demo_key_12345',
      orgId: org.id,
    },
  });
  console.log('âœ… Created Project:', project.name);

  console.log('ðŸŽ‰ Seeding completed successfully!');
}

main()
  .catch((e) => {
    console.error('âŒ Seeding failed:', e);
    process.exitCode = 1;
  })
  .finally(async () => {
    await prisma.$disconnect();
    if (typeof process.exitCode === 'number' && process.exitCode !== 0) {
      process.exit(process.exitCode);
    }
  });
