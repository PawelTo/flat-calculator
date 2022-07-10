Sample request for fetch data from Otodom

``$ curl -X POST 'https://www.otodom.pl/api/query' --header 'Content-Type: application/json' --data-raw '{"query":"query CountAds($filterAttributes: FilterAttributes, $filterLocations: FilterLocations) {\n  countAds(filterAttributes: $filterAttributes, filterLocations: $filterLocations) {\n    ... on CountAds {\n      count\n      __typename\n    }\n    __typename\n  }\n}\n","operationName":"CountAds","variables":{"filterAttributes":{"market":"SECONDARY","estate":"FLAT","transaction":"SELL","distanceRadius":0,"ownerTypeSingleSelect":"ALL","hasRemoteServices":false},"filterLocations":{"byGeoAttributes":[{"id":"cities_6-26"}]}}}'``

Response:
```{"data":{"countAds":{"count":11508,"__typename":"CountAds","__typename":"CountAds"}}}```
