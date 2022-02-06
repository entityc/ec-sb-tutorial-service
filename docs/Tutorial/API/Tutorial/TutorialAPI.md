# Tutorial

Represents an entire tutorial with modules and sessions.


## Create Tutorial

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/tutorial`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`identifier`|A unique identifier associated with this tutorial.|
|`overview`|Tutorial overview.|
|`summary`|The localized summary of the tutorial used when summarizing all tutorials in a view.|
|`title`|The localized title of the tutorial.|
|`createdUser.id`|Primary key of contextual `User` object.|

## Get Tutorial List

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/tutorial?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same Tutorial objects as returned by the Get Tutorial by ID endpoint.

## Get Tutorial by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/tutorial/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Tutorial` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`tutorialId`|_Primary Key_|
|`createdOn`|When the object was created.|
|`identifier`|A unique identifier associated with this tutorial.|
|`modifiedOn`|When the object was last modified.|
|`overview`|Tutorial overview.|
|`summary`|The localized summary of the tutorial used when summarizing all tutorials in a view.|
|`title`|The localized title of the tutorial.|
|`modules[]`|Array of `Module` objects.|

## Update Tutorial

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/tutorial/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Tutorial` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`identifier`|A unique identifier associated with this tutorial.|
|`overview`|Tutorial overview.|
|`summary`|The localized summary of the tutorial used when summarizing all tutorials in a view.|
|`title`|The localized title of the tutorial.|
|`createdUser.id`|Primary key of contextual `User` object.|

## Delete Tutorial by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/tutorial/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the Tutorial object to be deleted.|



