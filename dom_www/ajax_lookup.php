<?php

$r = array();

$fp = fopen("/tmp/can_data.txt","r");
while ($line = fgets($fp)) {
	$parts = explode("=",$line);
	$r[$parts[0]] = $parts[1];
	
}

echo json_encode($r);
