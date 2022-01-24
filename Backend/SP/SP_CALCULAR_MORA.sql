CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_CALCULAR_MORA`()
BEGIN
	DECLARE VMORA DOUBLE;
    
    BEGIN
		SELECT item_value
        INTO VMORA
        FROM items
        WHERE item_name = 'Morosidad';
        
		UPDATE payments
        SET arrears = (payment_total * (VMORA/100)), 
			status_id = (
				SELECT status_id
                FROM status
                WHERE status = 'Retrasado' AND description = 'Payment'
            )
        WHERE expire_date > curdate();
        
        -- BLOQUE PARA ENCONTRAR CLIENTES MOROSOS
        BEGIN
			CREATE TEMPORARY TABLE tmp_clients (
				SELECT cl.*
                FROM clients AS cl
                INNER JOIN sales AS s ON s.client_id = cl.client_id
                INNER JOIN payments_agreements AS pa ON pa.sale_id = s.sale_id
                WHERE 
            );
        END;
    END;
END