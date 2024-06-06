*** Variables ***
${URL}                    http://localhost:3001/

*** Settings ***
Library         SeleniumLibrary

*** Keywords ***
Dado que eu acesse o sistema
    Open Browser    url=${URL}   browser=Chrome

Fechar o navegador
    Close Browser