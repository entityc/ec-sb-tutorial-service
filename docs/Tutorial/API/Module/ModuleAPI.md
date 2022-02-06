# Module

Represents a module within the tutorial. A module is a big partition of the tutorial.


## Create Module

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/module`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`number`|Represents the module number.|
|`overview`|Module overview.|
|`summary`|The localized summary of the module used when sumarizing all modules of a tutorial.|
|`title`|The localized title of the module.|
|`tutorial.id`|Primary key of contextual `Tutorial` object.|

## Get Module List by *optional* Tutorial ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/module?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`tutorialId`|The unique ID of the parent `Tutorial` object.|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same Module objects as returned by the Get Module by ID endpoint.

## Get Module by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/module/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Module` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`moduleId`|_Primary Key_|
|`createdOn`|When the object was created.|
|`modifiedOn`|When the object was last modified.|
|`number`|Represents the module number.|
|`overview`|Module overview.|
|`summary`|The localized summary of the module used when sumarizing all modules of a tutorial.|
|`title`|The localized title of the module.|
|`sessions[]`|Array of `Session` objects.|
|`tutorial.id`|Primary key of contextual `Tutorial` object.|

## Update Module

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/module/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `Module` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`number`|Represents the module number.|
|`overview`|Module overview.|
|`summary`|The localized summary of the module used when sumarizing all modules of a tutorial.|
|`title`|The localized title of the module.|
|`tutorial.id`|Primary key of contextual `Tutorial` object.|

## Delete Module by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/module/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the Module object to be deleted.|



