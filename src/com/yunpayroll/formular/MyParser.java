package com.yunpayroll.formular;

import com.yunpayroll.formular.function.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyParser {
	
	private boolean createVariable = true;
	private static Map<String,Integer>		 operatorPrecedence	= new HashMap<String,Integer>(); 
	static {
		operatorPrecedence.put( "!"  , 8);			
		operatorPrecedence.put( "|"  , 7);																//	Intersect
		operatorPrecedence.put( "~"  , 6);																//	Negation
		operatorPrecedence.put( "^"  , 4);																//	Exponentiation
		operatorPrecedence.put( "%"  , 3);																//	Percentage
		operatorPrecedence.put( "*"  , 3);
		operatorPrecedence.put( "/"  , 3); 													//	Multiplication and Division
		operatorPrecedence.put( "+"  , 2);
		operatorPrecedence.put( "-"  , 2);														//	Addition and Subtraction
		operatorPrecedence.put( "&"  , 1);																//	Concatenation
		operatorPrecedence.put( ">"  , 0);
		operatorPrecedence.put( "<"  , 0);
		operatorPrecedence.put( "=="  , 0);
		operatorPrecedence.put( "!="  , 0);
		operatorPrecedence.put( ">=" , 0);
		operatorPrecedence.put( "<=" , 0);
		operatorPrecedence.put( "<>" , 0);
	}
	private static Map<String,Operator>		 operators	= new HashMap<String,Operator>(); 
	static {
		operators.put( "!"  , new Not());
		operators.put( "|"  , new Or());																//	Intersect
		operators.put( "||"  , new LogicOr());
		operators.put( "~"  , new SingleSub());																//	Negation
		operators.put( "%"  , new Mod());																//	Percentage
		operators.put( "^"  , new Pow());																//	Exponentiation
		operators.put( "*"  , new Multiply());
		operators.put( "/"  , new Divide()); 													//	Multiplication and Division
		operators.put( "+"  , new Add());
		operators.put( "-"  , new Subtract());														//	Addition and Subtraction
		operators.put( "&"  , new And());																//	Concatenation
		operators.put( "&&"  , new LogicAnd());
		operators.put( ">"  , new Gt());
		operators.put( "<"  , new Lt());
		operators.put( "=="  , new Equals());
		operators.put( ">=" , new Ge());
		operators.put( "<=" , new Le());
		operators.put( "<>" , new NotEquals());
		operators.put( "!=" , new NotEquals());
	}
	private static Map<String,Function> functions = new HashMap<String, Function>();
	static {
		functions.put( "if"  , new Iff());	
		functions.put( "sin"  , new Sin());	//	Intersect
		functions.put( "cos"  ,  new Cos());
		functions.put( "tan"  ,  new Tan());
		functions.put( "sqrt"  ,  new Sqrt());
		functions.put( "abs"  ,  new Abs());
		functions.put( "sum"  ,  new Sum());
		functions.put("rand", new Random());
		functions.put("str", new Str());
		functions.put("floor", new Floor());
		functions.put("ceil", new Ceil());
		functions.put("round", new Random());
	}
	private  Map<String,Object> variables = new HashMap<String, Object>();
	
	private List<Element> list = new ArrayList<>();

	public MyParser(){
		variables.put( "pi"  , BigDecimal.valueOf(Math.PI ));	
	}
	
	public List<Element>  parse(String str) throws ParseException,IOException{
		MyStack<Token> mystack = new MyStack<Token>();
		Reader r = new StringReader(str);
		JavaCharStream charStream = new JavaCharStream(r, 1, 1,1024); 
		TokenManager tm = new TokenManager(charStream);
		Token t;
		boolean expectingOperator = false;
		boolean expectingOperand = false;
		do{
			t = tm.getNextToken();
			if (t.kind ==TokenManager.OPERATOR  ) {
				if(expectingOperator ) {
					while(!mystack.isEmpty() 
							&& ((mystack.peek().kind==TokenManager.OPERATOR) 
							&& operatorPrecedence.get(t.image) <= operatorPrecedence.get(mystack.peek().image))){
						list.add(convert(mystack.pop()));
					}
					mystack.push(t);
					expectingOperator = false;
					expectingOperand = true;
				}else if(t.image.equals("+")){
					if(expectingOperator ) {
						mystack.push(t);
					}
					expectingOperator = false;
					expectingOperand = true;
				}else if(t.image.equals("-")){
					t.image = "~";
					mystack.push(t);
					expectingOperator = false;
					expectingOperand = true;
				}else if(t.image.equals("!")){
					t.image = "!";
					mystack.push(t);
					expectingOperator = false;
					expectingOperand = true;
				}else {
					throw new ParseException("不能解析的字符串"+t.image);
				}
				
			} else if (t.kind ==TokenManager.STRING) {
				Token t1 = tm.getNextToken();
				
				if(t1.kind==TokenManager.LRND){
					if( functions.containsKey(t.image)  ) {
						Token t2 = tm.getNextToken();
						if( t2.kind==TokenManager.RRND) {
							t.kind = TokenManager.FUNCTION;
							t.argCnt=0;
							expectingOperator = true;
							expectingOperand = false;
							list.add(convert(t));
						} else {
							t.argCnt=1;
							tm.input_stream.backup(t2.image.length());
							expectingOperator = false;
							expectingOperand = true;
							t.kind = TokenManager.FUNCTION;
							mystack.push(t1);
							mystack.push(t);
						}
						
						
					} else {
						throw new ParseException(t,"暂时还不支持的函数"+t.image);
					}
				} else 	{
					tm.input_stream.backup(t1.image.length());
					if(variables.containsKey(t.image) || isCreateVariable()){
						list.add(convert(t));
						expectingOperator = true;
						expectingOperand = false;
					} else{
						throw new ParseException(t,"没有定义的变量:"+t.image);
					}
					
					
				}
				
			} else if (t.kind ==TokenManager.NUMBER) {
				if(expectingOperator) {
					throw new ParseException(t,"希望此处是一个操作符");
				}
				list.add(convert(t));
				expectingOperator = true;
				expectingOperand = false;
			} else if(t.kind==TokenManager.LRND) {
				mystack.push(t);
			} else  if(t.kind==TokenManager.RRND ) {
				
				if(expectingOperator) {
					if(mystack.isEmpty() ) {
						throw new ParseException(t,"公式解析错误，多了个')'");
					}
					boolean hasLrnd = false;
					while(!mystack.isEmpty()) {
						Token t1 = mystack.pop();
						if(t1.kind!=TokenManager.LRND) {
							list.add(convert(t1)); 
						} else {
							hasLrnd= true;
							break;
						}
					}
					if(!hasLrnd) {
						throw new ParseException(t,"公式解析错误，多了个')'");
					}
				}  else {
					throw new ParseException(t,"期盼是一个操作对象");
				}
				
			}else if(t.kind==TokenManager.CONSTANT) {
				list.add(convert(t));
				expectingOperator = true;
				expectingOperand = false;
			}else if(t.image.equals(",")){
				boolean hasFunc = false;
				while(!mystack.isEmpty()) {
					if( mystack.peek().kind!=TokenManager.FUNCTION ) {
						list.add(convert(mystack.pop()));
					}  else {
						mystack.peek().argCnt++;
						hasFunc = true;
						break;
					}
				}
				if(!hasFunc) {
					throw new ParseException(t,"公式解析错误，缺少函数");
				}
				
				expectingOperator = false;
				expectingOperand = true;
			}
			
		}while(t.kind!=0);
		if(expectingOperand) {
			throw new ParseException(t,"期望有一个操作对象，不能以操作符结尾");
		}
		while(!mystack.isEmpty()) {
			if(mystack.peek().kind==TokenManager.LRND) {
				throw new ParseException("公式解析错误，期望有一个 ')'");
			}
			list.add(convert(mystack.pop()));
		}
		
		return list;
	}
	
	private Element convert(Token t) throws ParseException {
		try{
			switch (t.kind) {
				case TokenManager.CONSTANT:
					return new Constant( t.image.substring(1, t.image.length()-1) );
				case TokenManager.FUNCTION:
					if(!functions.containsKey(t.image)) {
						throw new ParseException("暂时不支持此函数"+t.image);
					}
					Function opFunc =  (Function)functions.get(t.image).clone();	
					opFunc.setNumOfArgs(t.argCnt);
					return opFunc;
					
				case TokenManager.NUMBER:
					return new Constant(BigDecimal.valueOf(Double.valueOf(t.image)));
				case TokenManager.OPERATOR:
					if(!operators.containsKey(t.image)) {
						throw new ParseException("不支持的操作"+t.image);
					}
					return (Element)(((AbstractOperator)operators.get(t.image)).clone());
				case TokenManager.STRING:
					return new Variable(t.image,variables.get(t.image));
				default:
					throw new ParseException("");
			}		
		}catch(CloneNotSupportedException cns) {
			throw new ParseException("clone exception.");
		}
	}
	public List<Element> getList(){
		return list;
	}
	
	
	public Object getValue() throws Exception{

		ElementVisitor ev  = new ElementVisitor();
		for(Element t :list) {
			t.visit(ev);
		}
		return ev.getValue();
	
	}
	
	public void addVar(String varname,Object value) {
		variables.put(varname, value);
	}
	public void addVars(Map<String,Object> vars) {
		variables.putAll(vars); 
	}
	
	public boolean isCreateVariable() {
		return createVariable;
	}

	public void setCreateVariable(boolean createVariable) {
		this.createVariable = createVariable;
	}

	public static void main(String[] args) throws Exception {
		long s =System.currentTimeMillis();
		for(int i=0;i<100000;i++) {
			MyParser parser = new MyParser();
			parser.addVar("a", 1.0);
			parser.addVar("b", 1.0);
			parser.addVar("c", 1.0);
			parser.addVar("d", 1.0);
			parser.parse("sum(1,2,3,sum(4,5),sum(1,1))+sum(1,2,3,sum(4,5),sum(1,1))+sum(1,2,3,sum(4,5),sum(1,1))+sum(1,2,3,sum(4,5),sum(1,1))+sum(1,2,3,sum(4,5),sum(1,1))+sum(1,2,3,sum(4,5),sum(1,1))");
			parser.getValue();
		}
		System.out.println(System.currentTimeMillis() - s);;
	}
}
