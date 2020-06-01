#!/bin/bash

curl --verbose -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{
   "listValue": [
     "good"
   ],
   "subEntity": {
     "url": "not_url"
   },
   "value1": "good1"
 }' 'http://localhost:8080/demo'