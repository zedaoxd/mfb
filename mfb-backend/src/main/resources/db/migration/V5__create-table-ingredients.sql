create table ingredients (
     calories FLOAT(8) NOT NULL,
     carbohydrates FLOAT(8) NOT NULL,
     fat FLOAT(53) NOT NULL,
     is_checked BOOLEAN NOT NULL DEFAULT FALSE,
     protein FLOAT(53) NOT NULL,
     created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
     updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
     id UUID NOT NULL,
     img_url VARCHAR(255),
     name VARCHAR(50),
     PRIMARY KEY (id)
);
