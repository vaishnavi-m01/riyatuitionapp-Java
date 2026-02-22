# Database Connection Guide (PostgreSQL)

To connect to your database and fix the "Connection refused" error, follow these steps:

## 1. Ensure PostgreSQL is Installed
If you don't have PostgreSQL installed, download and install it from [postgresql.org](https://www.postgresql.org/download/windows/).

## 2. Start the PostgreSQL Service
The "Connection refused" error usually means the database server isn't running.
1. Press `Win + R`, type `services.msc`, and press Enter.
2. Find **PostgreSQL** in the list.
3. If the Status is not "Running", right-click it and select **Start**.

## 3. Create the Database
Your app expects a database named `postgres` (as per `application.yaml`).
1. Open **pgAdmin 4** (installed with PostgreSQL) or use the command line.
2. Connect to your server (default password is set during installation).
3. Right-click on "Databases" -> **Create** -> **Database...**
4. Set "Database" name to `postgres` and click **Save**.

## 4. Connect in DBeaver
1. Open DBeaver.
2. Click **New Database Connection** (the plug icon).
3. Select **PostgreSQL**.
4. Set the following:
   - **Host**: `localhost`
   - **Port**: `5432`
   - **Database**: `postgres`
   - **Username**: `postgres`
   - **Password**: (The password you set during PostgreSQL installation)
5. Click **Test Connection**.

## 5. Update application.yaml (If needed)
If you set a different password during installation, update it in your `application.yaml`:
```yaml
spring:
  datasource:
    password: ${DB_PASSWORD:YOUR_NEW_PASSWORD}
```

> [!TIP]
> If you are still getting "Public Key Retrieval is not allowed", go to **Driver Properties** in DBeaver and set `allowPublicKeyRetrieval` to `true`.
