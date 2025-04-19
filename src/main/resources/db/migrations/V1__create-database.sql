DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'meu_banco') THEN
        CREATE DATABASE meu_banco;
END IF;
END
$$;