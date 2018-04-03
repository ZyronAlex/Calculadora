package com.computacaoognitiva.calculadora.model;

import java.util.HashMap;
import java.util.Stack;

public class AplCalcular {

	private HashMap<String,Operacao> operacoes  = new HashMap<String,Operacao>(); 
	private Stack<Integer> operandos = new Stack<Integer>();
	
	public AplCalcular(){
		operacoes.put("+", new Soma());
		operacoes.put("-", new Subtracao());
		operacoes.put("/", new Divisao());
		operacoes.put("*", new Multiplicacao());
	}
	
	//se acontecer excessao quem controla � o CCI, ou seja, a GUI
	public int calcular(String expressao) throws Exception{
		Operacao operacao;
		Integer resultado = 0;
		Integer operando1 = 0;
		Integer operando2 = 0;
		String[] elementos = expressao.split(" ");
		//percorre a lista de elementos
		for(String operando : elementos){
			//se for um operando
			if(operacoes.containsKey(operando)){
				//recupero a opera��o correta requerida
				operacao = operacoes.get(operando);
				//recupero os operandos da pilha
				operando2 = operandos.pop();
				operando1 = operandos.pop();
				//executo a operacao
				resultado = operacao.calcular(operando1, operando2);
				//insiro o resultado na pilha
				operandos.push(resultado);
			}else{
				//se n�o for um operando, insere na pilha
				operandos.push(Integer.parseInt(operando));
			}
		}
		//retorna o resultado final
		resultado = operandos.pop();
		return resultado;
	}
}
