# Session

Represents a session within a module. A session typically tries to teach a single concept.


## Create Session

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/session`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`discussion`|The discussion section of the session.|
|`number`|The session number.|
|`objective`|The objective of the session.|
|`title`|The title of the session.|
|`module.id`|Primary key of contextual `Module` object.|

## Get Session List by *optional* Module ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/session?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`moduleId`|The unique ID of the parent `Module` object.|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same Session objects as returned by the Get Session by ID endpoint.

## Get Session by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/session/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Session` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`sessionId`|_Primary Key_|
|`createdOn`|When the object was created.|
|`discussion`|The discussion section of the session.|
|`modifiedOn`|When the object was last modified.|
|`number`|The session number.|
|`objective`|The objective of the session.|
|`title`|The title of the session.|
|`exercises[]`|Array of `Exercise` objects.|
|`module.id`|Primary key of contextual `Module` object.|

## Update Session

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/session/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Session` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`discussion`|The discussion section of the session.|
|`number`|The session number.|
|`objective`|The objective of the session.|
|`title`|The title of the session.|
|`module.id`|Primary key of contextual `Module` object.|

## Delete Session by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/session/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the Session object to be deleted.|



