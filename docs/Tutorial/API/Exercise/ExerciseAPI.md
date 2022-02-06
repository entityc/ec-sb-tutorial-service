# Exercise

Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session.


## Create Exercise

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/exercise`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`number`|The exercise number.|
|`overview`|Exercise overview.|
|`session.id`|Primary key of contextual `Session` object.|

## Get Exercise List by *optional* Session ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/exercise?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`sessionId`|The unique ID of the parent `Session` object.|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same Exercise objects as returned by the Get Exercise by ID endpoint.

## Get Exercise by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/exercise/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Exercise` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`exerciseId`|_Primary Key_|
|`createdOn`|When the object was created.|
|`modifiedOn`|When the object was last modified.|
|`number`|The exercise number.|
|`overview`|Exercise overview.|
|`session.id`|Primary key of contextual `Session` object.|
|`steps[]`|Array of `Step` objects.|

## Update Exercise

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/exercise/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Exercise` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`number`|The exercise number.|
|`overview`|Exercise overview.|
|`session.id`|Primary key of contextual `Session` object.|

## Delete Exercise by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/exercise/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the Exercise object to be deleted.|



