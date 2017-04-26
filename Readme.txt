Table-Reservation

Table reservation system

App Description:

The app first opens a list of customers, with a filter by first and or last name. 
When A user clicks on a customer a grid of tables.
User can reserve/ unreserve only tables that were initially availaible ( Tables already reserved from the API response can't be modified )
The tables state is saved for each run ( every time the app opens the first list of tables is retrieved from the API, the next times the tables grid is viewied it doesn't call the API but retreive a saved session of the tables - this is done to avoid confusion, cause when a table is reserved there's no api to reflect to, so if everytime the API would be called it would retreive the same non modified list from the API) The App saves the customers and tables in a database for offline use.
Every 10 minutes A service run and sets all the tables saved in the databse to be reserved.

Technical Description:

The App is structured in MVP pattern
The background service is done through Alarm Manager that runs an intent service ( would have used Job Scheduler or Firebase job dispatcher but Job Scheduler requires os 21+ and Firebase job dispatcher doesn't guarantee to run exactly every 10 mintutes as required)
To test the background service more faster simply change the variable "UPDATE_TABLES_SERVICE_MINUTES_INTERVAL" in the TableReservationApplication class to 1 for example to run every minute.
Added Android unit test to test MainActivity class
Added unit test to test filter by first/last name functionality of Customers class
3rd party libraries used: retrofit2, RxJava/RxAndroid, Butterknife
Used Realm for offline database.
