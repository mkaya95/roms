<?php
class PersistenceManager {
  protected $pdo;
  public function __construct() {
    try {
      $this->pdo = new PDO("mysql:host=srv01.makinistproject.com;dbname=admin_roms;charset=utf8", "admin_roms", "mypass", [
       PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
       PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
      ]);
    } catch (PDOException  $e) {
      echo json_encode(['error' => $e->getMessage()]);
    }
  }
  public function execute($query, $params) {
    $prepared_statement = $this->pdo->prepare($query);
    if ($params){
      foreach($params as $key => $param) {
        $prepared_statement->bindValue($key, $param);
      }
    }
    $prepared_statement->execute();
    return $prepared_statement;
  }
  public function execute_insert($table, $record) {
    $insert = 'INSERT INTO '.$table.' ('.implode(", ", array_keys($record)).')VALUES (:'.implode(", :",  array_keys($record)).')';
    $prepared_statement = $this->execute($insert, $record);
    return $this->pdo->lastInsertId();
  }
  public function query($query, $params=[]) {
    $prepared_statement = $this->execute($query, $params);
    return $prepared_statement->fetchAll();
  }
  public function query_single($query, $params=[]) {
    $result = $this->query($query, $params);
    return reset($result);
  }
  public function update($query, $params) {
    $prepared_statement = $this->execute($query, $params);
  }
}
