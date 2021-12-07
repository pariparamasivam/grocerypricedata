DROP TABLE IF EXISTS grocery_price_data;
 
CREATE TABLE grocery_price_data (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  item_name VARCHAR(250) NOT NULL,
  dateadded date not null,
  price double
  
);