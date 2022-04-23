CREATE PROCEDURE `SP_CALCULAR_MORA`()
BEGIN
	DECLARE VMORA DOUBLE;

    -- BLOQUE DE EXCEPCIONES
    DECLARE PERROR VARCHAR(1000);
    DECLARE PERRORCODE VARCHAR(25);
    DECLARE PSTATE VARCHAR(25);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        GET DIAGNOSTICS CONDITION 1 @sql_state = RETURNED_SQLSTATE,
        @err_no = MYSQL_ERRNO, @message = MESSAGE_TEXT;
        SET PERROR = @message;
        SET PERRORCODE = @err_no;
        SET PSTATE = @sql_state;
        INSERT INTO errors_history (ocurred_at, error_code, error_description, error_message)
        VALUES(CURRENT_TIMESTAMP(), PERRORCODE, PSTATE, PERROR);
        ROLLBACK;
    END;
    
    BEGIN

        START TRANSACTION;
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
                INNER JOIN payments AS p ON p.payment_agreement_id = pa.payment_agreement_id
                WHERE p.expire_date
            );
        END;
    END;
END