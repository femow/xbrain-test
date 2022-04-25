# XBrain Test Store API - Documentation

## REST API - SELLERS
### baseUrl
URL: localhost:8080
### /sellers - GET
```
Method: GET
Description: Return an array of Seller objects.
Endpoint: /sellers
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
Description: Add a new Seller.
Endpoint: /sellers
Content-Type: Application/Json
Body: {
      name: string,
    }
}
```

### /sellers - UPDATE
```
Method: PATCH
Description: Update Seller's properties.
Endpoint: /sellers
Content-Type: Application/Json
Body: {
      name: string
    }
}
```

### /sellers/{id} - GET
```
Method: GET
Description: Return a Seller object.
Endpoint: /sellers/{id}
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
Description: Delete one Seller.
Endpoint: /sellers/{id}
Content-Type: Application/Json
PathVariable: {
    id: integer
}
```

## REST API - SALES
### baseUrl
URL: localhost:8080
### /sales - GET
```
Method: GET
Description: Return an array of Sale objects.
Endpoint: /sales
```

### /sales - POST
```
Method: POST
Description: Add a new Sale.
Endpoint: /sales
Content-Type: Application/Json
Body: {
      dateOfSale: string,
      value: number,
      sellerId: integer
    }
}
```

### /sales - UPDATE
```
Method: PATCH
Description: Update Sales's properties.
Endpoint: /sales
Content-Type: Application/Json
Body: {
      name: string
    }
}
```


### /sales/{id} - GET
```
Method: GET
Description: Return a Sale object.
Endpoint: /sales/{id}
PathVariable: {
    id: integer
}
```


### /sales/{id} - DELETE
```
Method: DELETE
Description: Delete one Sale.
Endpoint: /sales/{id}
Content-Type: Application/Json
PathVariable: {
    id: integer
}
```

## Lines Coverage
![image](https://user-images.githubusercontent.com/84784549/165157173-06818cde-8c6c-41a1-add3-5c6cbb44b7d5.png)
