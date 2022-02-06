# Step

An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise.


## Create Step

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/step`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`instructions`|Step content.|
|`number`|The step number.|
|`exercise.id`|Primary key of contextual `Exercise` object.|

## Get Step List by *optional* Exercise ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/step?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`exerciseId`|The unique ID of the parent `Exercise` object.|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same Step objects as returned by the Get Step by ID endpoint.

## Get Step by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/step/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Step` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`stepId`|_Primary Key_|
|`createdOn`|When the object was created.|
|`instructions`|Step content.|
|`modifiedOn`|When the object was last modified.|
|`number`|The step number.|
|`exercise.id`|Primary key of contextual `Exercise` object.|

## Update Step

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/step/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Step` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`instructions`|Step content.|
|`number`|The step number.|
|`exercise.id`|Primary key of contextual `Exercise` object.|

## Delete Step by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/step/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the Step object to be deleted.|



