
*** Settings ***
Library         SeleniumLibrary


*** Test Cases ***

Abrir o navegador e acessar o site do sistema
    Open Browser    url=http:localhost:3001   browser=Chrome


Preencher os campos do formulário
    Input Text       id:form-nome        Julio Oliveira
    Input Text       id:form-cargo       Desenvolvedor
    Input Text       id:form-imagem      https://picsum.photos/100/100
    Click Element    class:lista-suspensa
    Click Element    //option[contains(.,'Programação')]
    Sleep    10s
    Click Element    id:form-botao
    Element Should Be Visible    class:colaborador
    Sleep    5s