import { useState } from "react";
import Botao from "../Botao";
import CampoTexto from "../CampoTexto";
import ListaSuspensa from "../ListaSuspensa";
import "./Formulario.css";

const defaultState = { value: "", error: true };
const Formulario = (props) => {
  const [nome, setNome] = useState(defaultState);
  const [cargo, setCargo] = useState(defaultState);
  const [imagem, setImagem] = useState(defaultState);
  const [time, setTime] = useState(defaultState);
  const [mostraErro, setMostraErro] = useState(false);

  const aoSalvar = (evento) => {
    evento.preventDefault();
    setMostraErro(true);
    console.log(formValido());
    if (formValido()) {
      props.aoColaboradorCadastrado({
        nome: nome.value,
        cargo: cargo.value,
        imagem: imagem.value,
        time: time.value,
      });
      setNome(defaultState);
      setCargo(defaultState);
      setImagem(defaultState);
      setTime(defaultState);
      setMostraErro(false);
    }
  };
  const formValido = () => {
    console.log(nome.error, cargo.error, time.error);
    return !nome.error && !cargo.error && !time.error;
  };
  return (
    <section className='formulario'>
      <form onSubmit={aoSalvar}>
        <h2>Preencha os dados para criar o card do colaborador</h2>
        <CampoTexto
          obrigatorio={true}
          label='Nome'
          placeholder='Digite seu nome'
          valor={nome.value}
          aoAlterado={(valor, erro) => setNome({ value: valor, error: erro })}
          id='form-nome'
          mostraErro={mostraErro}
          mensagemErro='O campo nome deve ser preenchido'
        />
        <CampoTexto
          obrigatorio={true}
          label='Cargo'
          placeholder='Digite seu cargo'
          valor={cargo.value}
          aoAlterado={(valor, erro) => setCargo({ value: valor, error: erro })}
          id='form-cargo'
          mostraErro={mostraErro}
          mensagemErro='O campo cargo deve ser preenchido'
        />
        <CampoTexto
          label='Imagem'
          placeholder='Digite o endereço da imagem'
          valor={imagem.value}
          aoAlterado={(valor, erro) =>
            setImagem({ value: valor, error: false })
          }
          id='form-imagem'
          mostraErro={false}
        />
        <ListaSuspensa
          obrigatorio={true}
          label='Time'
          itens={props.times}
          valor={time.value}
          aoAlterado={(valor, erro) => setTime({ value: valor, error: erro })}
          id='form-times'
          mostraErro={mostraErro}
          mensagemErro='Selecione um time para essa pessoa'
        />
        <Botao id='form-botao'>Criar Card</Botao>
      </form>
    </section>
  );
};

export default Formulario;
