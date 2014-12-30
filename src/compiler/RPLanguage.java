package compiler;
import graphic_interface.Interface;

import code_generator.*;

public class RPLanguage implements RPLanguageConstants {
        private STable table=new STable();
      

/*----------------------------------------------------------------------------------------REGOLE DI PRODUZIONE*/
  final public Node start() throws ParseException {
 Node start_nptr, grammatica_nptr;
 Interface.displayPrint.println("<start> ::= <grammatica>;");
    grammatica_nptr = grammatica();
    jj_consume_token(0);
    start_nptr=grammatica_nptr;
    {if (true) return start_nptr;}
    throw new Error("Missing return statement in function");
  }

//<grammatica> ::= <regole>;
  final public Node grammatica() throws ParseException {
 Node grammatica_nptr, regole_nptr;
 Interface.displayPrint.println("<grammatica> ::= <regole>;");
    regole_nptr = regole();
    grammatica_nptr=regole_nptr;
    {if (true) return grammatica_nptr;}
    throw new Error("Missing return statement in function");
  }

//<regole> ::= <regola> <regola_queue>;
  final public Node regole() throws ParseException {
 Node regole_nptr, regola_nptr, regola_queue_nptr;
 Interface.displayPrint.println("<regole> ::= <regola> <regola_queue>;");
    regola_nptr = regola();
    regola_queue_nptr = regola_queue(regola_nptr);
    regole_nptr=regola_queue_nptr;
    {if (true) return regole_nptr;}
    throw new Error("Missing return statement in function");
  }

//<regola_queue> ::= <regole> | eps;
  final public Node regola_queue(Node regola_nptr_inherited) throws ParseException {
 Node regola_queue_nptr, regole_nptr;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OP_LOOKAHEAD:
    case NON_TERM:
    	Interface.displayPrint.println("<regola_queue> ::= <regole>;");
      regole_nptr = regole();
    //-------------------------------------------------------------------------------------------------------GESTIONE REGOLA
    //se la "gestione della regola" non ha comportato modifiche
    //all'albero sintattico allra ci porocede normalmente
    if(regola_nptr_inherited!=null)
                regola_queue_nptr=new Node(Symbol.REGOLE, "regole", regola_nptr_inherited, regole_nptr);/*conservare questa riga se si vuole eliminare
   																								  la "gestione della regola"*/
        //altrimenti
        else
                //se la "gestione della regola" ha cancellato il nodo di una regola
                //ma non il nodo delle regole successive allora le regole successive
                //vengono sostituite alla regola eliminata.
                if(regole_nptr!=null)
                        regola_queue_nptr=new Node(Symbol.REGOLE, "regole", regole_nptr);

                //se la "gestione della regola" ha cancellato il nodo di una regola
                //e anche quello delle regole successive, allora l'insieme delle regole
                //si annulla.
                else
                        regola_queue_nptr=null;

        {if (true) return regola_queue_nptr;}/*conservare questa riga se si vuole eliminare la "gestione della regola"*/
        //-------------------------------------------------------------------------------------------------------/GESTIONE REGOLA

      break;
    default:
      jj_la1[0] = jj_gen;
      Interface.displayPrint.println("<regola_queue> ::= eps;");

    regola_queue_nptr=regola_nptr_inherited;
    {if (true) return regola_queue_nptr;}
    }
    throw new Error("Missing return statement in function");
  }

//<regola> ::= [OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD PARCHIUSA_T] NON_TERM PUO_ESSERE <corpo> PV;
  final public Node regola() throws ParseException {
 Node regola_nptr, corpo_nptr;
 Token OP_LOOKAHEAD_t, NUM_LOOKAHEAD_t, NON_TERM_t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OP_LOOKAHEAD:
    	Interface.displayPrint.println("<regola> ::= OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD PARCHIUSA_T NON_TERM PUO_ESSERE <corpo> PV;");
      OP_LOOKAHEAD_t = jj_consume_token(OP_LOOKAHEAD);
      jj_consume_token(PARAPERTA_T);
      NUM_LOOKAHEAD_t = jj_consume_token(NUM_LOOKAHEAD);
      jj_consume_token(PARCHIUSA_T);
      NON_TERM_t = jj_consume_token(NON_TERM);
      jj_consume_token(PUO_ESSERE);
      corpo_nptr = corpo();
      jj_consume_token(PV);
    regola_nptr=new Node(Symbol.REGOLA, "regola", new Node(Symbol.NUM_LOOKAHEAD, NUM_LOOKAHEAD_t.image), new Node(Symbol.NON_TERM, NON_TERM_t.image), corpo_nptr);

        //-------------------------------------------------------------------------------------------------------GESTIONE REGOLA
        //se la regola non è presente in tabella
        //allora la si inserisce e il parsing non subisce alterazioni
        if(!table.contains(NON_TERM_t.image))
                table.put(NON_TERM_t.image, new Info(regola_nptr, corpo_nptr, Integer.parseInt(NUM_LOOKAHEAD_t.image)));
        //se la regola è già presente allora il suo corpo lo si sposta
        //nel corpo della regola già definita
        else{
                //preleviamo il corpo della regola già presente nella tabella
                Node corpo=table.get(NON_TERM_t.image).getCorpoRegola();

                //se la regola prelevata dall tabella ha un corpo
                //formato da un solo simbolo terminale o non terminale
                //allora il corpo della regola già definita viene
                //modificato in un corpo contenente una "pipe" di elementi.
                if(corpo.getSon()==null){

                        //aggiungiamo, in pipe, il corpo della regola corrente
                        //alla regola già definita
                        String val=corpo.getVal();
                        Symbol sym=corpo.getSymbol();
                        corpo.setVal("oppure");
                        corpo.setSymbol(Symbol.OPPURE);
                        corpo.setSon(new Node(sym, val));
                        corpo.getSon().setBrother(corpo_nptr);
                }
                //altrimenti
                else

                        //se la regola prelevata dalla tabella ha un corpo
                        //formato da "pipe" di terminali e non terminali
                        if(corpo.getSon().getBrother()!=null){

                                //puntiamo all'ultimo insieme di elementi in "pipe"
                                while(corpo.getSon().getBrother().getVal()=="oppure")
                                        corpo=corpo.getSon().getBrother();

                                //aggiungiamo il corpo della regola corrente alla
                                //"pipe" (corpo) della regola già definita
                                Node elementi=corpo.getSon().getBrother();
                                corpo.getSon().setBrother(new Node(Symbol.OPPURE, "oppure", elementi, corpo_nptr));
                        }
                        //se la regola prelevata dall tabella ha un corpo
                        //formato da una sola concatenazione di simboli terminali e non
                        //terminali, allora il corpo della regola già definita viene
                        //modificato in un corpo contenente una "pipe" di elementi.
                        else
                        {
                          //aggiungiamo, in pipe, il corpo della regola corrente
                          //alla regola già definita
                          Node elementi=corpo.getSon();
                          corpo.setVal("oppure");
                          corpo.setSymbol(Symbol.OPPURE);
                          corpo.setSon(elementi);
                          corpo.setBrother(corpo_nptr);
                        }

                //se il numero di simboli di lookahead della regola corrente è maggioredel numero di simboli di lookahead
                //della regola di destinazione allora la regola di destinazione assume il numer di simboli di lookahead
                //della regola corrente
                if(table.get(NON_TERM_t.image).getLookaheadNumber() <  Integer.parseInt(NUM_LOOKAHEAD_t.image))
                        table.get(NON_TERM_t.image).getRegola().getSon().setVal(NUM_LOOKAHEAD_t.image);
                //il link alla regola corrente viene annullato
                regola_nptr=null;
        }
        //-------------------------------------------------------------------------------------------------------/GESTIONE REGOLA
    {if (true) return regola_nptr;}/*conservare questa riga se si vuole eliminare la "gestione della regola"*/

      break;
    case NON_TERM:
    	Interface.displayPrint.println("<regola> ::= NON_TERM PUO_ESSERE <corpo> PV;");
      NON_TERM_t = jj_consume_token(NON_TERM);
      jj_consume_token(PUO_ESSERE);
      corpo_nptr = corpo();
      jj_consume_token(PV);
    regola_nptr=new Node(Symbol.REGOLA, "regola", new Node(Symbol.NUM_LOOKAHEAD, "1"), new Node(Symbol.NON_TERM, NON_TERM_t.image), corpo_nptr);

    //-------------------------------------------------------------------------------------------------------GESTIONE REGOLA
        //se la regola non è presente in tabella
        //allora la si inserisce e il parsing non subisce alterazioni
        if(!table.contains(NON_TERM_t.image))
                table.put(NON_TERM_t.image, new Info(regola_nptr, corpo_nptr, 1));
        //se la regola è già presente allora il suo corpo lo si sposta
        //nel corpo della regola già definita
        else{
          //preleviamo il corpo della regola già presente nella tabella
                Node corpo=table.get(NON_TERM_t.image).getCorpoRegola();

                //se la regola prelevata dall tabella ha un corpo
                //formato da un solo simbolo terminale o non terminale
                //allora il corpo della regola già definita viene
                //modificato in un corpo contenente una "pipe" di elementi.
                if(corpo.getSon()==null){

                        //aggiungiamo, in pipe, il corpo della regola corrente
                        //alla regola già definita
                        String val=corpo.getVal();
                        Symbol sym=corpo.getSymbol();
                        corpo.setVal("oppure");
                        corpo.setSymbol(Symbol.OPPURE);
                        corpo.setSon(new Node(sym, val));
                        corpo.getSon().setBrother(corpo_nptr);
                }
                //altrimenti
                else

                        //se la regola prelevata dalla tabella ha un corpo
                        //formato da "pipe" di terminali e non terminali
                        if(corpo.getSon().getBrother()!=null){

                                //puntiamo all'ultimo insieme di elementi in "pipe"
                                while(corpo.getSon().getBrother().getVal()=="oppure")
                                        corpo=corpo.getSon().getBrother();

                                //aggiungiamo il corpo della regola corrente alla
                                //"pipe" (corpo) della regola già definita
                                Node elementi=corpo.getSon().getBrother();
                                corpo.getSon().setBrother(new Node(Symbol.OPPURE, "oppure", elementi, corpo_nptr));
                        }
                        //se la regola prelevata dall tabella ha un corpo
                        //formato da una sola concatenazione di simboli terminali e non
                        //terminali, allora il corpo della regola già definita viene
                        //modificato in un corpo contenente una "pipe" di elementi.
                        else
                        {
                          //aggiungiamo, in pipe, il corpo della regola corrente
                          //alla regola già definita
                          Node elementi=corpo.getSon();
                          corpo.setVal("oppure");
                          corpo.setSymbol(Symbol.OPPURE);
                          corpo.setSon(elementi);
                          corpo.setBrother(corpo_nptr);
                        }
                //il link alla regola corrente viene annullato	
                regola_nptr=null;
        }
        //-------------------------------------------------------------------------------------------------------GESTIONE REGOLA
    {if (true) return regola_nptr;}/*conservare questa riga se si vuole eliminare la "gestione della regola"*/

      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//<corpo> ::= <elementi> <elementi_queue>;
  final public Node corpo() throws ParseException {
 Node corpo_nptr, elementi_nptr, elementi_queue_nptr;
 Interface.displayPrint.println("<corpo> ::=  <elementi> <elementi_queue>;");
    elementi_nptr = elementi();
    elementi_queue_nptr = elementi_queue(elementi_nptr);
    elementi_nptr=elementi_queue_nptr;
    {if (true) return elementi_nptr;}
    throw new Error("Missing return statement in function");
  }

//<elementi_queue> ::= PIPE <corpo> | eps;
  final public Node elementi_queue(Node elementi_nptr_inherited) throws ParseException {
 Node elementi_queue_nptr, corpo_nptr;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PIPE:
    	Interface.displayPrint.println("<elementi_queue> ::=  PIPE <corpo>;");
      jj_consume_token(PIPE);
      corpo_nptr = corpo();
    elementi_queue_nptr=new Node(Symbol.OPPURE, "oppure", elementi_nptr_inherited, corpo_nptr);
    {if (true) return elementi_queue_nptr;}
      break;
    default:
      jj_la1[2] = jj_gen;
      Interface.displayPrint.println("<elementi_queue> ::= eps;");

    elementi_queue_nptr=elementi_nptr_inherited;
    {if (true) return elementi_queue_nptr;}
    }
    throw new Error("Missing return statement in function");
  }

//<elementi> ::= <elemento> <elemento_queue>;
  final public Node elementi() throws ParseException {
 Node elementi_nptr, elemento_nptr, elemento_queue_nptr;
 Interface.displayPrint.println("<elementi> ::=  <elemento> <elemento_queue>;");
    elemento_nptr = elemento();
    elemento_queue_nptr = elemento_queue(elemento_nptr);
    elementi_nptr=elemento_queue_nptr;
    {if (true) return elementi_nptr;}
    throw new Error("Missing return statement in function");
  }

//<elemento_queue> ::= <elementi> | eps;
  final public Node elemento_queue(Node elemento_nptr_inherited) throws ParseException {
 Node elemento_queue_nptr, elementi_nptr;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PARAPERTA_Q:
    case EPS:
    case NON_TERM:
    case TERM:
    	Interface.displayPrint.println("<elemento_queue> ::= <elementi>;");
      elementi_nptr = elementi();
    elemento_queue_nptr=new Node(Symbol.CONCAT, "concat", elemento_nptr_inherited, elementi_nptr);
    {if (true) return elemento_queue_nptr;}
      break;
    default:
      jj_la1[3] = jj_gen;
      Interface.displayPrint.println("<elemento_queue> ::=  eps;");

    elemento_queue_nptr=elemento_nptr_inherited;
    {if (true) return elemento_queue_nptr;}
    }
    throw new Error("Missing return statement in function");
  }

//<elemento> ::= TERM | NON_TERM | eps | PARAPERTA_Q <elementi> PARCHIUSA_Q;
  final public Node elemento() throws ParseException {
 Node elemento_nptr, elementi_nptr;
 Token TERM_t, NON_TERM_t, EPS_t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TERM:
    	Interface.displayPrint.println("<elemento> ::=  TERM;");
      TERM_t = jj_consume_token(TERM);
    elemento_nptr=new Node(Symbol.TERM, TERM_t.image);
    {if (true) return elemento_nptr;}
      break;
    case NON_TERM:
    	Interface.displayPrint.println("<elemento> ::=  NON_TERM;");
      NON_TERM_t = jj_consume_token(NON_TERM);
    elemento_nptr=new Node(Symbol.NON_TERM, NON_TERM_t.image);
    {if (true) return elemento_nptr;}
      break;
    case EPS:
    	Interface.displayPrint.println("<elemento> ::=  EPS;");
      EPS_t = jj_consume_token(EPS);
    elemento_nptr=new Node(Symbol.EPS, EPS_t.image);
    {if (true) return elemento_nptr;}
      break;
    case PARAPERTA_Q:
    	Interface.displayPrint.println("<elemento> ::= PARAPERTA_Q <elementi> PARCHIUSA_Q;");
      jj_consume_token(PARAPERTA_Q);
      elementi_nptr = elementi();
      jj_consume_token(PARCHIUSA_Q);
    elemento_nptr=new Node(Symbol.OPZ, "?", elementi_nptr);
    {if (true) return elemento_nptr;}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public RPLanguageTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[5];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x300000,0x300000,0x20000,0x612000,0x612000,};
   }

  /** Constructor with InputStream. */
  public RPLanguage(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public RPLanguage(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new RPLanguageTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public RPLanguage(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new RPLanguageTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public RPLanguage(RPLanguageTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(RPLanguageTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[23];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 5; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 23; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
