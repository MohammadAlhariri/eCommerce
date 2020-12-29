<?php
    echo "Configs File";
    $Configs = (object) array(
    'hostname' => 'localhost',
    'USERNAME' => 'USERNAME',
    'password' => 'PASSWORD',
    'database' => 'DATABASE'
    );

    $CONNECTION = MYSQL_CONNECTION($Configs['hostname'], $configs['username'], $configs['password'], $configs['database'])
    or die("Not Connected")

?>