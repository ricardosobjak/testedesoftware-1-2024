*** Variables ***
${URL}                    http://localhost:3001/

*** Settings ***
Resource    ../main.robot

*** Keywords ***
Dado que eu acesse o sistema
    Open Browser    url=${URL}   browser=Chrome

Fechar o navegador
    Close Browser