# XBrain Test Store API - Documentation

## REST API
### baseUrl
URL: localhost:8080
### /sellers - GET
```
Method: GET
Description: Return an array of Sale objects.
Endpoint: localhost:8080/sellers
Parameters: {
    startDate: string
}
startDate Parameter Info: {
     type: date,
     format: YYYY-MM-DD
}
usageExemple: localhost:8080/sellers?startDate=2022-04-25
 
```
### /sellers - POST
```
Method: POST
Description: Add a new Sale.
Endpoint: localhost:8080/sellers
Content-Type: Application/Json
Body: {
      dateOfSale: string,
      value: number,
      sellerId: integer
    }
}
```

### /sellers - UPDATE
```
Method: PATCH
Description: Add a new Sale.
Endpoint: localhost:8080/sellers
Content-Type: Application/Json
Body: {
      dateOfSale: string,
      value: number,
      sellerId: integer
    }
}
```

### /sellers/{id} - GET
```
Method: GET
Description: Return a Sale object.
Endpoint: localhost:8080/sellers/{id}
PathVariable: {
    id: integer
}
Parameters: {
    startDate: string
}
startDate Parameter Info: {
     type: date,
     format: YYYY-MM-DD
}
usageExemple: localhost:8080/sellers/1?startDate=2022-04-25
```


### /sellers/{id} - DELETE
```
Method: DELETE
Description: Delete one Sale.
Endpoint: localhost:8080/sellers/{id}
Content-Type: Application/Json
PathVariable: {
    id: integer
}
```


## Lines Coverage
![image](https://user-images.githubusercontent.com/84784549/165157173-06818cde-8c6c-41a1-add3-5c6cbb44b7d5.png)
