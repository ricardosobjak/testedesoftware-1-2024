import { useState } from "react";
import "./ListaSuspensa.css";

const ListaSuspensa = (props) => {
  const [erro, setErro] = useState(true);
  return (
    <div className='lista-suspensa'>
      <label>{props.label}</label>
      <select
        onChange={(evento) => {
          setErro(evento.target.value === "");
          props.aoAlterado(evento.target.value, evento.target.value === "");
        }}
        value={props.valor}>
        <option value=''></option>
        {props.itens.map((item) => {
          return <option key={item}>{item}</option>;
        })}
      </select>
      {props.mostraErro && erro ? (
        <p id={`${props.id}-erro`}>{props.mensagemErro}</p>
      ) : (
        ""
      )}
    </div>
  );
};

export default ListaSuspensa;
