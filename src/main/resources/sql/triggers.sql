-- -----------------------------------------------------
-- Table `Users`
-- -----------------------------------------------------
CREATE OR REPLACE FUNCTION createdAtUser()
	RETURNS TRIGGER
AS '
BEGIN
	NEW.created_at := CURRENT_DATE;
	RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER AD_created_at_Users
  BEFORE INSERT ON public.Users
  FOR EACH ROW
EXECUTE PROCEDURE createdAtUser();