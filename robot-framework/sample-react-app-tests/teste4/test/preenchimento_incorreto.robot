
*** Settings ***
Resource          ../resource/main.robot
Test Setup        Dado que eu acesse o sistema
Test Teardown     Fechar o navegador


*** Test Cases ***

Verificar se quando um campo obrigatório não for preenchido corretamente o sistema exibe uma mensagem de campo obrigatório
    Dado que clique no botão criar card
    Então o sistema deve apresentar mensagem de campo obrigatório