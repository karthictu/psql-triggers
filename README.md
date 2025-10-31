# psql-triggers
A solution using PSQL Triggers to efficiently perform CRUD operations 

The project focuses on designing two scalable solutions that can be seemlessly integrated to efficiently perform data storage, transformation and extraction.

## Download Functionality

### Standard solution
1. User makes an API call to download data from the database.
2. API follows below process:
   - Fetch data from the database.
   - Extract fields from JSONB transportation column.
   - Extract and transform other fields.
   - Populate JSON/XLS response.
3. Return JSON/XLS to the user.
   
### Challenges with the Solution
- Data transformation and extraction happens in middleware which consumes significant amounts of CPU/memory.
- When allocated CPU/memory is exhausted, k8s spins up new instances to serve the process and incoming requets.
- If it takes long enough time, gateway returns **504 Gateway Timeout Error**.

The above mentioned solution is therefore inefficient and not scalable.

### Proposed Solution
To overcome this, proposed solution will have following components:
- New table (destination_table)
- SQL Trigger Procedures
- API Endpoints
<img width="268" height="168" alt="download_flow" src="https://github.com/user-attachments/assets/20e2db84-8319-4e5f-933a-1e6ffecccfa1" />

### SQL Table
- This table holds data in download-ready format.
- This eliminates the need for transformation in middleware.

### SQL Trigger Procedures
- As data in ingested into the source_table, below steps are executed:
   1. Extract values from columns expected in JSON/XLS.
   2. Transform and extract transportation data.
   3. Save the data in the destination table.
- When the record's state is COMPLETED or FAILED, it will automatically be removed from the destination table.

### API Endpoints
- Standalone service endpoint that serves the download functionality.
- Supports downloading data in JSON or XLS format.
<img width="521" height="111" alt="download_api_flow drawio" src="https://github.com/user-attachments/assets/8025f36d-558b-4be9-bc33-4da563190455" />

### Advantages of Proposed Solution
- **Process Delegation**- CPU/memory overhead is delegated to database.
- **Lightweight middleware** - Responsible for serving only the incoming requests.
- **Process Automation** - Database is responbile for populating data
  - Currency Conversion to USD
  - Auto Update/Insert/Delete in destination table to keep in sync with source table.
- **Loose Coupling** - No dependency of middleware over data manipulation.
- **Scalability**
- **Availablity**
  
### Possible Challenges
- Slight delay in data reflecting into destination table from source table if the process in made asynchronous.
- Maintainence of two table, database functions and triggers

## Upload Functionality

### Standard Solution
1. User makes an API call to upload data to the database.
2. API follows below process:
   - Validate JSON keys/XLS headers and data.
   - If XLS, map data to domain/entity models.
   - Fetch data from the database.
   - Update values for updateable fields.
   - Transform JSONB transportation field.
   - Store complete data in the database.

### Challenges with the Solution
- Data transformation and extraction happens in middleware which consumes significant amounts of CPU/memory.
- When allocated CPU/memory is exhausted, k8s spins up new instances to serve the process and incoming requets.
- If it takes long enough time, gateway returns **504 Gateway Timeout Error**.

### Proposed Solution
This solution will have following components:
- New table (upload_table)
- SQL Trigger Procedures
- API Endpoints

<img width="474" height="181" alt="upload_api_flow" src="https://github.com/user-attachments/assets/641c2822-76aa-4137-9166-ea45c12f9843" />

### SQL Table
- This table holds data in the format received in JSON/XLS file.

### SQL Trigger Procedures
- As data in ingested into the upload_table, below steps are executed:
   1. Existing record is fetched from the source_table.
   2. Normalized column are directly updated.
   3. Transportation field is fetched and specific fields are updated.
   4. Save the data in the source table.
- When a record is successfully updated in the source_table, it is deleted from the upload_table.

### API Endpoints
- Standalone service endpoint that serves the upload functionality.

### Advantages of Proposed Solution
- Same as Download Functionality

### Possible Challenges
- Same as Download Functionality
