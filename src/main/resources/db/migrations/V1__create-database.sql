DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'easyNutrition') THEN
        CREATE DATABASE easyNutrition;
END IF;
END
$$;