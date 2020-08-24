Feature: Be able to booking

    Scenario: Get bookings
        Given url "http://192.168.1.140:8080"
        Given path "/bookings"
        When method get
        Then status 200
    
    Scenario: Get bookings/id
        Given url "http://192.168.1.140:8080"
        Given path "/bookings/18"
        When method get
        Then status 200
        Then print response
        And match response.id == 18
        

    Scenario: Post bookings
        Given url "http://192.168.1.140:8080"
        Given path "/bookings"
        And request 
        """
        {
            "id":20,
            "comments": "abcd",
            "vehiculeNumberPlate" : "{{$guid}}",
            "status": "booked",
            "countryCode": "ES",
            "vatNumber": "12345678"
        }
        """
        And header Content-Type = 'application/json; charset=utf-8'
        When method post
        Then status 201

    Scenario: put bookings
        Given url "http://192.168.1.140:8080"
        Given path "/bookings/26"
        And request { "comments": "abcd" }
        When method put
        Then status 200

    Scenario: delete bookings
        Given url "http://192.168.1.140:8080/bookings/26"
        When method delete
        Then status 202