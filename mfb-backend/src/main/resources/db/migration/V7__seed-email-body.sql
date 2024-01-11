INSERT INTO emails_body (id, email_body_type, body)
VALUES (
           gen_random_uuid(),
           'VERIFY_EMAIL',
           '
                <p>Clique abaixo para validar seu e-mail:</p>
                <a href=[[href]]
                style="
                    font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
                    box-sizing: border-box;
                    font-size: 14px;
                    color: #FFF;
                    text-decoration: none;
                    line-height: 2em;
                    font-weight: bold;
                    text-align: center;
                    cursor: pointer;
                    display: inline-block;
                    border-radius: 5px;
                    text-transform: capitalize;
                    background-color: #f5a967;
                    margin: 0;
                    padding: 3px 6px;"
                >
                    Clique aqui
                </a>
                <p>Se você não realizou o cadastro em nosso site, por favor ignore este e-mail.</p>
           '
);

INSERT INTO emails_body (id, email_body_type, body)
VALUES (
           gen_random_uuid(),
           'RESET_PASSWORD',
           '
                <p>Clique abaixo para recuperar sua senha:</p>
                <a href=[[href]]
                    style="
                        font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
                        box-sizing: border-box;
                        font-size: 14px;
                        color: #FFF;
                        text-decoration: none;
                        line-height: 2em;
                        font-weight: bold;
                        text-align: center;
                        cursor: pointer;
                        display: inline-block;
                        border-radius: 5px;
                        text-transform: capitalize;
                        background-color: #f5a967;
                        margin: 0;
                        padding: 3px 6px;"
                >
                	Clique aqui
                </a>
                <p>Se você não solicitou a recuperação de senha, por favor ignore este e-mail.</p>
           '
);