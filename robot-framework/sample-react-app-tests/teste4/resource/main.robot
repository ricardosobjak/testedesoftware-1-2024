*** Settings ***
Library           SeleniumLibrary
Library           FakerLibrary    locale=pt_BR  #pip install robotframework-faker

Resource          shared/setup_teardown.robot
Resource          pages/cadastro.robot