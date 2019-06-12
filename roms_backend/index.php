<?php
header( "Content-type:  application/json" );

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

require 'vendor/autoload.php';
require 'PersistenceManager.class.php';

Flight::register('pm', 'PersistenceManager');


Flight::route('/', function(){
  Flight::json(array('msg' => 'hello'));
});


Flight::route('GET /settings', function(){
  $settings = Flight::pm()->query("SELECT * FROM settings ", []);
  Flight::json($settings);
});

Flight::route('GET /settings/@label', function($label){
  $settings = Flight::pm()->query_single("SELECT * FROM settings WHERE label=:label", [':label'=>$label]);
  Flight::json($settings);
});

Flight::route('GET /login/@email/@password', function($email, $password){
  $login = Flight::pm()->query_single("SELECT count(*) cnt FROM user WHERE email=:email and password=:password", [':email'=>$email, ':password'=>md5($password)]);
  Flight::json(['isLogin' => $login['cnt']]);
});


Flight::route('GET /order/@table_number', function($table_number){
  $order = Flight::pm()->query("SELECT  O.*, F.name, F.desc, F.price  FROM orders O JOIN foods F ON F.id = O.food_id WHERE O.table_number=:table_number and is_finished = 0", [':table_number' => $table_number]);
  Flight::json($order);
});
Flight::route('GET /order/@table_number/close_table', function($table_number){
  $order = Flight::pm()->update("UPDATE orders SET is_finished = 1 WHERE table_number = :table_number", [':table_number' => $table_number]);
  Flight::json($order);
});

Flight::route('GET /order/make_order/@table_number/@food_id', function($table_number, $food_id){
  $order = Flight::pm()->execute_insert("orders",[
    'table_number' => $table_number,
    'food_id' => $food_id,
  ]);
  Flight::json([]);
});


Flight::route('GET /get_overview', function(){
  $overview = Flight::pm()->query("SELECT table_number  FROM orders  WHERE is_finished = 0 GROUP BY table_number", []);

  $result = [];
  foreach ($overview as $key => $value) {
    $result[] = $value['table_number'];
  }

  Flight::json([implode($result,",")]);
});

Flight::route('GET /foods', function(){
  $foods = Flight::pm()->query("SELECT *  FROM foods", []);
  Flight::json($foods);
});

Flight::route('POST /settings/update', function(){
  $info = Flight::request()->data;
  unset($info['id']);

    $query= 'UPDATE settings SET ';
    $params = [':label' => $info['label']];
    foreach ($info as $key => $value) {
      $query .= $key . '=:' . $key . ', ';
      $params[':' . $key] = $value;
    }
    $query = rtrim($query,', ') . ' WHERE label=:label';
    Flight::pm()->update($query, $params);
    $settings = Flight::pm()->query_single("SELECT * FROM settings WHERE label=:label", [':label'=>$info['label']]);
    echo json_encode([$settings], JSON_UNESCAPED_UNICODE);
});


Flight::start();

?>
