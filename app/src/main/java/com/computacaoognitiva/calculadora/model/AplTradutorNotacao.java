package com.computacaoognitiva.calculadora.model;

import java.util.HashMap;
import java.util.Stack;

public class AplTradutorNotacao {

	public static String ocidentalToPolonesa(String ocidental){
		Stack<String> pilha = new Stack<String>();
		String novaNotacao = "";
		String buffer = "";
		//percorro a nota��o A ser convertida
		for(char operando : ocidental.toCharArray()){
			if(operando == '('){
				if(buffer != ""){
					pilha.push(buffer);
					buffer = "";
				}
			}
			else if(operando == ')'){
				//processar buffer
				novaNotacao += processaNotacao(buffer);
		
				//verifico se a pilha est� vazia, se n�o removo da pilha e processo nota��o
				if(!pilha.isEmpty()){
					buffer = pilha.pop();
					//processo
					novaNotacao += processaNotacao(buffer);
				}

				buffer = "";
			}
			else if(operando == ' '){
				//se for espaco nao faz nada
			}else{
				//adciono no buffer
				buffer +=operando;
			}
		}
		novaNotacao +=processaNotacao(buffer);
		
		//removo da pilha e processo a nota��o
		while(!pilha.empty()){
			buffer = pilha.pop();
			novaNotacao += processaNotacao(buffer);
		}
		
		return novaNotacao;
	}

	public static String processaNotacao(String notacao){
		Stack<String> pilhaOperadores = new Stack<String>();
		HashMap<String,Integer> operadores = new HashMap<String,Integer>();	
		String novaNotacao = "";
		
		//defino prioridades dos operadores
		operadores.put("+", 1);
		operadores.put("-", 1);
		operadores.put("*", 2);
		operadores.put("/", 2);
		
		//Percorro a notação inserida
		for(char operando : notacao.toCharArray()){
			//verifico se é um OPERADOR
			if(operadores.containsKey(""+operando)){
				//verifico se a pilha está vazia e insiro
				if(pilhaOperadores.empty()){
					pilhaOperadores.push(""+operando);//paulo, não me mate s2
				}else{
					//verifico se a prioridade do operando é menor que o ultimo da pilha
					if(operadores.get(pilhaOperadores.peek()) > operadores.get(operando+"")){
						//desempilho tudo e jogo na string
						while(!pilhaOperadores.isEmpty()){
							novaNotacao += pilhaOperadores.pop()+" ";
						} 
						//empilho o operador corrente
						pilhaOperadores.push(operando+"");
					}
					else if(operadores.get(pilhaOperadores.peek()) < operadores.get(operando+"")){ //Se a prioridade da pilha for menor
						pilhaOperadores.push(operando+"");
					}
					else{ //Se a prioridade for igual
						novaNotacao += pilhaOperadores.pop()+" ";
						pilhaOperadores.push(operando+"");
					}
				}
			}else if(operando != ' ' && operando != '(' && operando != ')'){ //Se não for um OPERADOR e não for espaço
				novaNotacao += operando+" ";
			}
		}
		
		//desempilho tudo e jogo na string
		while(!pilhaOperadores.isEmpty()){
			novaNotacao += pilhaOperadores.pop()+" ";
		} 
		
		return novaNotacao;
	}
}
