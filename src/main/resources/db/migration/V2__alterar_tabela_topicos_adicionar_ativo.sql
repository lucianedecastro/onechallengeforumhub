ALTER TABLE topicos ADD ativo BOOLEAN NOT NULL;
UPDATE topicos SET ativo = TRUE;