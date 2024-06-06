
*** Settings ***
Resource          setup_teardown.robot
Test Setup        Dado que eu acesse o sistema
Test Teardown     Fechar o navegador

*** Variables ***
${URL}                    http://localhost:3001/
${CAMPO_NOME}             id:form-nome
${CAMPO_CARGO}            id:form-cargo
${CAMPO_IMAGEM}           id:form-imagem
${CAMPO_TIME}             class:lista-suspensa
${BOTAO_CARD}             id:form-botao
${OPCAO_PROGRAMACAO}      //option[contains(.,'Programação')]
${OPCAO_FRONT}            //option[contains(.,'Front-End')]
${OPCAO_DADOS}            //option[contains(.,'Data Science')]
${OPCAO_DEVOPS}           //option[contains(.,'Devops')] 
${OPCAO_UX}               //option[contains(.,'UX e Design')]
${OPCAO_MOBILE}           //option[contains(.,'Mobile')]
${OPCAO_INOVACAO}         //option[contains(.,'Inovação e Gestão')]


*** Test Cases ***

Verificar se ao preencher os campos do formulário corretamente os dados são inseridos na lista e se um novo card é criado no time esperado
    Dado que preencha os campos do formulário
    E clique no botão criar card
    Então idenetificar o card no time esperado


*** Keywords ***

Dado que preencha os campos do formulário
    Input Text       ${CAMPO_NOME}        Julio Oliveira
    Input Text       ${CAMPO_CARGO}       Desenvolvedor
    Input Text       ${CAMPO_IMAGEM}      https://picsum.photos/100/100
    Click Element    ${CAMPO_TIME}
    Click Element    ${OPCAO_PROGRAMACAO}

E clique no botão criar card
    Click Element    ${BOTAO_CARD}

Então idenetificar o card no time esperado
    Element Should Be Visible    class:colaborador