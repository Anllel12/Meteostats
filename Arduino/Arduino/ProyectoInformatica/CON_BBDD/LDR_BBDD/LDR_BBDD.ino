#include <Wire.h>

int sensor = 34; // Pin analógico al que está conectado el LDR

#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define MYSQL_LOGLEVEL      1

#include <MySQL_Generic.h>

#define USING_HOST_NAME     false

#if USING_HOST_NAME
  // Optional using hostname, and Ethernet built-in DNS lookup
  char server[] = "your_account.ddns.net"; // change to your server's hostname/URL
#else
  IPAddress server(195, 235, 211, 197);
#endif

// IPAddress server(195, 235, 211, 197);

////// AJUSTES BBDD
uint16_t server_port = 3306;    //3306;

//char default_database[] = "pi2_bd_meteostats";           //"test_arduino";
char default_database[] = "primeteostats";
//char default_table[]    = "hello_arduino";          //"test_arduino";

//String default_value    = "Hello, Arduino!"; 

/////// INICIALIZAMOS QUERY
String INSERT_SQL = "";

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;

/////// DATOS WIFI
char ssid[] = "A52s Alex";             // your network SSID (name)
char pass[] = "12345678";         // your network password

//char ssid[] = "wireless-uem";             // your network SSID (name)
//char pass[] = "";         // your network password

/////// DATOS BBDD
char user[]         = "pri_meteostats";              // MySQL user login username
char password[]     = "pri_meteostats";          // MySQL user login password
char tipoSensor[] = "LDR";

unsigned long delayTime;


void setup() {
  Serial.begin(9600);
  while (!Serial);

  MYSQL_DISPLAY1("\nStarting Basic_Insert_ESP on", ARDUINO_BOARD);
  MYSQL_DISPLAY(MYSQL_MARIADB_GENERIC_VERSION);

  /////// CONFIGURACION WIFI
  MYSQL_DISPLAY1("Connecting to", ssid);
  
  WiFi.begin(ssid, pass);
  
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    MYSQL_DISPLAY0(".");
  }

  ////// IMPRIMIR INFORMACION DE CONEXION
  MYSQL_DISPLAY1("Connected to network. My IP address is:", WiFi.localIP());

  MYSQL_DISPLAY3("Connecting to SQL Server @", server, ", Port =", server_port);
  MYSQL_DISPLAY5("User =", user, ", PW =", password, ", DB =", default_database);

  delay(1000);


  /////CONFIG LDR

  
  
}


void runInsert()
{
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);
  if (conn.connected())
  {
    MYSQL_DISPLAY(INSERT_SQL);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(INSERT_SQL.c_str()) )
    {
      MYSQL_DISPLAY("Insert error");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}

void loop()
{
   int id = 1;
        float lecluz = analogRead(sensor);
        Serial.print("Luminosity = ");
        Serial.print(lecluz);
        Serial.println(" lux");

        MYSQL_DISPLAY("Connecting...");
        INSERT_SQL = "INSERT INTO pri_meteostats.sensores (id_sensor, tipo_sensor, fecha, lectura1, usuario) VALUES (0, '"+String(tipoSensor)+"', now(), '" + String(lecluz, 3) + "', 1) ";
        Serial.println(INSERT_SQL);

        //if (conn.connect(server, server_port, user, password))
        delay(2000);
        //Cambiar primera condicion a lectura != 0
        if ((true) && (conn.connected() || conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL))
        {
          runInsert();
        } 
        else 
        {
          MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
        }
        //delay de 30 minutos
  delay(1800000);
}
