package net.sf.orcc.cal.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import net.sf.orcc.cal.services.CalGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalCalParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_REAL", "RULE_DECIMAL", "RULE_OCTAL", "RULE_HEX", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'package'", "';'", "'actor'", "'unit'", "'import'", "'.'", "'.*'", "':'", "'end'", "'='", "'('", "','", "')'", "'==>'", "'function'", "'-->'", "'var'", "'procedure'", "'begin'", "'>'", "'priority'", "'schedule'", "'fsm'", "'=>'", "'regexp'", "'|'", "'*'", "'?'", "'local'", "'action'", "'guard'", "'do'", "'initialize'", "'['", "']'", "'repeat'", "':='", "'foreach'", "'in'", "'..'", "'if'", "'then'", "'else'", "'elsif'", "'while'", "'||'", "'or'", "'&&'", "'and'", "'^'", "'&'", "'!='", "'<'", "'<='", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'/'", "'div'", "'mod'", "'**'", "'~'", "'not'", "'#'", "'for'", "'true'", "'false'", "'bool'", "'float'", "'half'", "'double'", "'int'", "'size'", "'List'", "'type'", "'String'", "'uint'", "'@'"
    };
    public static final int RULE_HEX=8;
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int RULE_REAL=5;
    public static final int RULE_INT=10;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int RULE_OCTAL=7;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int RULE_DECIMAL=6;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=9;
    public static final int RULE_SL_COMMENT=12;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=13;
    public static final int RULE_ANY_OTHER=14;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalCalParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalCalParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalCalParser.tokenNames; }
    public String getGrammarFileName() { return "InternalCal.g"; }



     	private CalGrammarAccess grammarAccess;
     	
        public InternalCalParser(TokenStream input, CalGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "AstEntity";	
       	}
       	
       	@Override
       	protected CalGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleAstEntity"
    // InternalCal.g:67:1: entryRuleAstEntity returns [EObject current=null] : iv_ruleAstEntity= ruleAstEntity EOF ;
    public final EObject entryRuleAstEntity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstEntity = null;


        try {
            // InternalCal.g:68:2: (iv_ruleAstEntity= ruleAstEntity EOF )
            // InternalCal.g:69:2: iv_ruleAstEntity= ruleAstEntity EOF
            {
             newCompositeNode(grammarAccess.getAstEntityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstEntity=ruleAstEntity();

            state._fsp--;

             current =iv_ruleAstEntity; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstEntity"


    // $ANTLR start "ruleAstEntity"
    // InternalCal.g:76:1: ruleAstEntity returns [EObject current=null] : ( (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )? ( (lv_imports_3_0= ruleImport ) )* ( (lv_annotations_4_0= ruleAstAnnotation ) )* ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) ) ) ;
    public final EObject ruleAstEntity() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        Token lv_name_6_0=null;
        Token otherlv_8=null;
        Token lv_name_9_0=null;
        AntlrDatatypeRuleToken lv_package_1_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_annotations_4_0 = null;

        EObject lv_actor_7_0 = null;

        EObject lv_unit_10_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:79:28: ( ( (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )? ( (lv_imports_3_0= ruleImport ) )* ( (lv_annotations_4_0= ruleAstAnnotation ) )* ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) ) ) )
            // InternalCal.g:80:1: ( (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )? ( (lv_imports_3_0= ruleImport ) )* ( (lv_annotations_4_0= ruleAstAnnotation ) )* ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) ) )
            {
            // InternalCal.g:80:1: ( (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )? ( (lv_imports_3_0= ruleImport ) )* ( (lv_annotations_4_0= ruleAstAnnotation ) )* ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) ) )
            // InternalCal.g:80:2: (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )? ( (lv_imports_3_0= ruleImport ) )* ( (lv_annotations_4_0= ruleAstAnnotation ) )* ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) )
            {
            // InternalCal.g:80:2: (otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==15) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalCal.g:80:4: otherlv_0= 'package' ( (lv_package_1_0= ruleQualifiedName ) ) otherlv_2= ';'
                    {
                    otherlv_0=(Token)match(input,15,FOLLOW_3); 

                        	newLeafNode(otherlv_0, grammarAccess.getAstEntityAccess().getPackageKeyword_0_0());
                        
                    // InternalCal.g:84:1: ( (lv_package_1_0= ruleQualifiedName ) )
                    // InternalCal.g:85:1: (lv_package_1_0= ruleQualifiedName )
                    {
                    // InternalCal.g:85:1: (lv_package_1_0= ruleQualifiedName )
                    // InternalCal.g:86:3: lv_package_1_0= ruleQualifiedName
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstEntityAccess().getPackageQualifiedNameParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_4);
                    lv_package_1_0=ruleQualifiedName();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstEntityRule());
                    	        }
                           		set(
                           			current, 
                           			"package",
                            		lv_package_1_0, 
                            		"net.sf.orcc.cal.Cal.QualifiedName");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,16,FOLLOW_5); 

                        	newLeafNode(otherlv_2, grammarAccess.getAstEntityAccess().getSemicolonKeyword_0_2());
                        

                    }
                    break;

            }

            // InternalCal.g:106:3: ( (lv_imports_3_0= ruleImport ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==19) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalCal.g:107:1: (lv_imports_3_0= ruleImport )
            	    {
            	    // InternalCal.g:107:1: (lv_imports_3_0= ruleImport )
            	    // InternalCal.g:108:3: lv_imports_3_0= ruleImport
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstEntityAccess().getImportsImportParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_5);
            	    lv_imports_3_0=ruleImport();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstEntityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"imports",
            	            		lv_imports_3_0, 
            	            		"net.sf.orcc.cal.Cal.Import");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalCal.g:124:3: ( (lv_annotations_4_0= ruleAstAnnotation ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==94) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalCal.g:125:1: (lv_annotations_4_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:125:1: (lv_annotations_4_0= ruleAstAnnotation )
            	    // InternalCal.g:126:3: lv_annotations_4_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstEntityAccess().getAnnotationsAstAnnotationParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_6);
            	    lv_annotations_4_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstEntityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_4_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalCal.g:142:3: ( (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) ) | (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            else if ( (LA4_0==18) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalCal.g:142:4: (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) )
                    {
                    // InternalCal.g:142:4: (otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) ) )
                    // InternalCal.g:142:6: otherlv_5= 'actor' ( (lv_name_6_0= RULE_ID ) ) ( (lv_actor_7_0= ruleAstActor ) )
                    {
                    otherlv_5=(Token)match(input,17,FOLLOW_3); 

                        	newLeafNode(otherlv_5, grammarAccess.getAstEntityAccess().getActorKeyword_3_0_0());
                        
                    // InternalCal.g:146:1: ( (lv_name_6_0= RULE_ID ) )
                    // InternalCal.g:147:1: (lv_name_6_0= RULE_ID )
                    {
                    // InternalCal.g:147:1: (lv_name_6_0= RULE_ID )
                    // InternalCal.g:148:3: lv_name_6_0= RULE_ID
                    {
                    lv_name_6_0=(Token)match(input,RULE_ID,FOLLOW_7); 

                    			newLeafNode(lv_name_6_0, grammarAccess.getAstEntityAccess().getNameIDTerminalRuleCall_3_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getAstEntityRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_6_0, 
                            		"org.eclipse.xtext.common.Terminals.ID");
                    	    

                    }


                    }

                    // InternalCal.g:164:2: ( (lv_actor_7_0= ruleAstActor ) )
                    // InternalCal.g:165:1: (lv_actor_7_0= ruleAstActor )
                    {
                    // InternalCal.g:165:1: (lv_actor_7_0= ruleAstActor )
                    // InternalCal.g:166:3: lv_actor_7_0= ruleAstActor
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstEntityAccess().getActorAstActorParserRuleCall_3_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_actor_7_0=ruleAstActor();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstEntityRule());
                    	        }
                           		set(
                           			current, 
                           			"actor",
                            		lv_actor_7_0, 
                            		"net.sf.orcc.cal.Cal.AstActor");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalCal.g:183:6: (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) )
                    {
                    // InternalCal.g:183:6: (otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) ) )
                    // InternalCal.g:183:8: otherlv_8= 'unit' ( (lv_name_9_0= RULE_ID ) ) ( (lv_unit_10_0= ruleAstUnit ) )
                    {
                    otherlv_8=(Token)match(input,18,FOLLOW_3); 

                        	newLeafNode(otherlv_8, grammarAccess.getAstEntityAccess().getUnitKeyword_3_1_0());
                        
                    // InternalCal.g:187:1: ( (lv_name_9_0= RULE_ID ) )
                    // InternalCal.g:188:1: (lv_name_9_0= RULE_ID )
                    {
                    // InternalCal.g:188:1: (lv_name_9_0= RULE_ID )
                    // InternalCal.g:189:3: lv_name_9_0= RULE_ID
                    {
                    lv_name_9_0=(Token)match(input,RULE_ID,FOLLOW_8); 

                    			newLeafNode(lv_name_9_0, grammarAccess.getAstEntityAccess().getNameIDTerminalRuleCall_3_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getAstEntityRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_9_0, 
                            		"org.eclipse.xtext.common.Terminals.ID");
                    	    

                    }


                    }

                    // InternalCal.g:205:2: ( (lv_unit_10_0= ruleAstUnit ) )
                    // InternalCal.g:206:1: (lv_unit_10_0= ruleAstUnit )
                    {
                    // InternalCal.g:206:1: (lv_unit_10_0= ruleAstUnit )
                    // InternalCal.g:207:3: lv_unit_10_0= ruleAstUnit
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstEntityAccess().getUnitAstUnitParserRuleCall_3_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_unit_10_0=ruleAstUnit();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstEntityRule());
                    	        }
                           		set(
                           			current, 
                           			"unit",
                            		lv_unit_10_0, 
                            		"net.sf.orcc.cal.Cal.AstUnit");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstEntity"


    // $ANTLR start "entryRuleImport"
    // InternalCal.g:231:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalCal.g:232:2: (iv_ruleImport= ruleImport EOF )
            // InternalCal.g:233:2: iv_ruleImport= ruleImport EOF
            {
             newCompositeNode(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;

             current =iv_ruleImport; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalCal.g:240:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) ) otherlv_2= ';' ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:243:28: ( (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) ) otherlv_2= ';' ) )
            // InternalCal.g:244:1: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) ) otherlv_2= ';' )
            {
            // InternalCal.g:244:1: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) ) otherlv_2= ';' )
            // InternalCal.g:244:3: otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_3); 

                	newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
                
            // InternalCal.g:248:1: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard ) )
            // InternalCal.g:249:1: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard )
            {
            // InternalCal.g:249:1: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard )
            // InternalCal.g:250:3: lv_importedNamespace_1_0= ruleQualifiedNameWithWildCard
            {
             
            	        newCompositeNode(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildCardParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_4);
            lv_importedNamespace_1_0=ruleQualifiedNameWithWildCard();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getImportRule());
            	        }
                   		set(
                   			current, 
                   			"importedNamespace",
                    		lv_importedNamespace_1_0, 
                    		"net.sf.orcc.cal.Cal.QualifiedNameWithWildCard");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_2, grammarAccess.getImportAccess().getSemicolonKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalCal.g:278:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalCal.g:279:2: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalCal.g:280:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalCal.g:287:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // InternalCal.g:290:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalCal.g:291:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalCal.g:291:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalCal.g:291:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
                
            // InternalCal.g:298:1: (kw= '.' this_ID_2= RULE_ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==20) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalCal.g:299:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,20,FOLLOW_3); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_9); 

            	    		current.merge(this_ID_2);
            	        
            	     
            	        newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildCard"
    // InternalCal.g:319:1: entryRuleQualifiedNameWithWildCard returns [String current=null] : iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF ;
    public final String entryRuleQualifiedNameWithWildCard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildCard = null;


        try {
            // InternalCal.g:320:2: (iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF )
            // InternalCal.g:321:2: iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameWithWildCardRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameWithWildCard=ruleQualifiedNameWithWildCard();

            state._fsp--;

             current =iv_ruleQualifiedNameWithWildCard.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildCard"


    // $ANTLR start "ruleQualifiedNameWithWildCard"
    // InternalCal.g:328:1: ruleQualifiedNameWithWildCard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildCard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:331:28: ( (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) )
            // InternalCal.g:332:1: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            {
            // InternalCal.g:332:1: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            // InternalCal.g:333:5: this_QualifiedName_0= ruleQualifiedName (kw= '.*' )?
            {
             
                    newCompositeNode(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 
                
            pushFollow(FOLLOW_10);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;


            		current.merge(this_QualifiedName_0);
                
             
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:343:1: (kw= '.*' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==21) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalCal.g:344:2: kw= '.*'
                    {
                    kw=(Token)match(input,21,FOLLOW_2); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameWithWildCard"


    // $ANTLR start "entryRuleAstUnit"
    // InternalCal.g:357:1: entryRuleAstUnit returns [EObject current=null] : iv_ruleAstUnit= ruleAstUnit EOF ;
    public final EObject entryRuleAstUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstUnit = null;


        try {
            // InternalCal.g:358:2: (iv_ruleAstUnit= ruleAstUnit EOF )
            // InternalCal.g:359:2: iv_ruleAstUnit= ruleAstUnit EOF
            {
             newCompositeNode(grammarAccess.getAstUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstUnit=ruleAstUnit();

            state._fsp--;

             current =iv_ruleAstUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstUnit"


    // $ANTLR start "ruleAstUnit"
    // InternalCal.g:366:1: ruleAstUnit returns [EObject current=null] : ( () otherlv_1= ':' ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )* otherlv_5= 'end' ) ;
    public final EObject ruleAstUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_5=null;
        EObject lv_functions_2_0 = null;

        EObject lv_procedures_3_0 = null;

        EObject lv_variables_4_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:369:28: ( ( () otherlv_1= ':' ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )* otherlv_5= 'end' ) )
            // InternalCal.g:370:1: ( () otherlv_1= ':' ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )* otherlv_5= 'end' )
            {
            // InternalCal.g:370:1: ( () otherlv_1= ':' ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )* otherlv_5= 'end' )
            // InternalCal.g:370:2: () otherlv_1= ':' ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )* otherlv_5= 'end'
            {
            // InternalCal.g:370:2: ()
            // InternalCal.g:371:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstUnitAccess().getAstUnitAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,22,FOLLOW_11); 

                	newLeafNode(otherlv_1, grammarAccess.getAstUnitAccess().getColonKeyword_1());
                
            // InternalCal.g:380:1: ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )*
            loop7:
            do {
                int alt7=4;
                alt7 = dfa7.predict(input);
                switch (alt7) {
            	case 1 :
            	    // InternalCal.g:380:2: ( (lv_functions_2_0= ruleFunction ) )
            	    {
            	    // InternalCal.g:380:2: ( (lv_functions_2_0= ruleFunction ) )
            	    // InternalCal.g:381:1: (lv_functions_2_0= ruleFunction )
            	    {
            	    // InternalCal.g:381:1: (lv_functions_2_0= ruleFunction )
            	    // InternalCal.g:382:3: lv_functions_2_0= ruleFunction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstUnitAccess().getFunctionsFunctionParserRuleCall_2_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_11);
            	    lv_functions_2_0=ruleFunction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstUnitRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"functions",
            	            		lv_functions_2_0, 
            	            		"net.sf.orcc.cal.Cal.Function");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalCal.g:399:6: ( (lv_procedures_3_0= ruleAstProcedure ) )
            	    {
            	    // InternalCal.g:399:6: ( (lv_procedures_3_0= ruleAstProcedure ) )
            	    // InternalCal.g:400:1: (lv_procedures_3_0= ruleAstProcedure )
            	    {
            	    // InternalCal.g:400:1: (lv_procedures_3_0= ruleAstProcedure )
            	    // InternalCal.g:401:3: lv_procedures_3_0= ruleAstProcedure
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstUnitAccess().getProceduresAstProcedureParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_11);
            	    lv_procedures_3_0=ruleAstProcedure();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstUnitRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"procedures",
            	            		lv_procedures_3_0, 
            	            		"net.sf.orcc.cal.Cal.AstProcedure");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalCal.g:418:6: ( (lv_variables_4_0= ruleConstantVariable ) )
            	    {
            	    // InternalCal.g:418:6: ( (lv_variables_4_0= ruleConstantVariable ) )
            	    // InternalCal.g:419:1: (lv_variables_4_0= ruleConstantVariable )
            	    {
            	    // InternalCal.g:419:1: (lv_variables_4_0= ruleConstantVariable )
            	    // InternalCal.g:420:3: lv_variables_4_0= ruleConstantVariable
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstUnitAccess().getVariablesConstantVariableParserRuleCall_2_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_11);
            	    lv_variables_4_0=ruleConstantVariable();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstUnitRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"variables",
            	            		lv_variables_4_0, 
            	            		"net.sf.orcc.cal.Cal.ConstantVariable");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            otherlv_5=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_5, grammarAccess.getAstUnitAccess().getEndKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstUnit"


    // $ANTLR start "entryRuleConstantVariable"
    // InternalCal.g:448:1: entryRuleConstantVariable returns [EObject current=null] : iv_ruleConstantVariable= ruleConstantVariable EOF ;
    public final EObject entryRuleConstantVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstantVariable = null;


        try {
            // InternalCal.g:449:2: (iv_ruleConstantVariable= ruleConstantVariable EOF )
            // InternalCal.g:450:2: iv_ruleConstantVariable= ruleConstantVariable EOF
            {
             newCompositeNode(grammarAccess.getConstantVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConstantVariable=ruleConstantVariable();

            state._fsp--;

             current =iv_ruleConstantVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConstantVariable"


    // $ANTLR start "ruleConstantVariable"
    // InternalCal.g:457:1: ruleConstantVariable returns [EObject current=null] : (this_VariableDeclaration_0= ruleVariableDeclaration ( (lv_constant_1_0= '=' ) ) ( (lv_value_2_0= ruleAstExpression ) ) otherlv_3= ';' ) ;
    public final EObject ruleConstantVariable() throws RecognitionException {
        EObject current = null;

        Token lv_constant_1_0=null;
        Token otherlv_3=null;
        EObject this_VariableDeclaration_0 = null;

        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:460:28: ( (this_VariableDeclaration_0= ruleVariableDeclaration ( (lv_constant_1_0= '=' ) ) ( (lv_value_2_0= ruleAstExpression ) ) otherlv_3= ';' ) )
            // InternalCal.g:461:1: (this_VariableDeclaration_0= ruleVariableDeclaration ( (lv_constant_1_0= '=' ) ) ( (lv_value_2_0= ruleAstExpression ) ) otherlv_3= ';' )
            {
            // InternalCal.g:461:1: (this_VariableDeclaration_0= ruleVariableDeclaration ( (lv_constant_1_0= '=' ) ) ( (lv_value_2_0= ruleAstExpression ) ) otherlv_3= ';' )
            // InternalCal.g:462:5: this_VariableDeclaration_0= ruleVariableDeclaration ( (lv_constant_1_0= '=' ) ) ( (lv_value_2_0= ruleAstExpression ) ) otherlv_3= ';'
            {
             
                    newCompositeNode(grammarAccess.getConstantVariableAccess().getVariableDeclarationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_12);
            this_VariableDeclaration_0=ruleVariableDeclaration();

            state._fsp--;

             
                    current = this_VariableDeclaration_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:470:1: ( (lv_constant_1_0= '=' ) )
            // InternalCal.g:471:1: (lv_constant_1_0= '=' )
            {
            // InternalCal.g:471:1: (lv_constant_1_0= '=' )
            // InternalCal.g:472:3: lv_constant_1_0= '='
            {
            lv_constant_1_0=(Token)match(input,24,FOLLOW_13); 

                    newLeafNode(lv_constant_1_0, grammarAccess.getConstantVariableAccess().getConstantEqualsSignKeyword_1_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getConstantVariableRule());
            	        }
                   		setWithLastConsumed(current, "constant", true, "=");
            	    

            }


            }

            // InternalCal.g:485:2: ( (lv_value_2_0= ruleAstExpression ) )
            // InternalCal.g:486:1: (lv_value_2_0= ruleAstExpression )
            {
            // InternalCal.g:486:1: (lv_value_2_0= ruleAstExpression )
            // InternalCal.g:487:3: lv_value_2_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getConstantVariableAccess().getValueAstExpressionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_4);
            lv_value_2_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getConstantVariableRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_3, grammarAccess.getConstantVariableAccess().getSemicolonKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstantVariable"


    // $ANTLR start "entryRuleAstActor"
    // InternalCal.g:515:1: entryRuleAstActor returns [EObject current=null] : iv_ruleAstActor= ruleAstActor EOF ;
    public final EObject entryRuleAstActor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstActor = null;


        try {
            // InternalCal.g:516:2: (iv_ruleAstActor= ruleAstActor EOF )
            // InternalCal.g:517:2: iv_ruleAstActor= ruleAstActor EOF
            {
             newCompositeNode(grammarAccess.getAstActorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstActor=ruleAstActor();

            state._fsp--;

             current =iv_ruleAstActor; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstActor"


    // $ANTLR start "ruleAstActor"
    // InternalCal.g:524:1: ruleAstActor returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )? otherlv_5= ')' ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )? otherlv_9= '==>' ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )? otherlv_13= ':' ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )* ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )? ( (lv_priorities_22_0= rulePriority ) )* otherlv_23= 'end' ) ;
    public final EObject ruleAstActor() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_23=null;
        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;

        EObject lv_inputs_6_0 = null;

        EObject lv_inputs_8_0 = null;

        EObject lv_outputs_10_0 = null;

        EObject lv_outputs_12_0 = null;

        EObject lv_functions_14_0 = null;

        EObject lv_procedures_15_0 = null;

        EObject lv_actions_16_0 = null;

        EObject lv_initializes_17_0 = null;

        EObject lv_stateVariables_18_0 = null;

        EObject lv_localFsms_19_0 = null;

        EObject lv_scheduleFsm_20_0 = null;

        EObject lv_scheduleRegExp_21_0 = null;

        EObject lv_priorities_22_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:527:28: ( ( () otherlv_1= '(' ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )? otherlv_5= ')' ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )? otherlv_9= '==>' ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )? otherlv_13= ':' ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )* ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )? ( (lv_priorities_22_0= rulePriority ) )* otherlv_23= 'end' ) )
            // InternalCal.g:528:1: ( () otherlv_1= '(' ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )? otherlv_5= ')' ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )? otherlv_9= '==>' ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )? otherlv_13= ':' ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )* ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )? ( (lv_priorities_22_0= rulePriority ) )* otherlv_23= 'end' )
            {
            // InternalCal.g:528:1: ( () otherlv_1= '(' ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )? otherlv_5= ')' ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )? otherlv_9= '==>' ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )? otherlv_13= ':' ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )* ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )? ( (lv_priorities_22_0= rulePriority ) )* otherlv_23= 'end' )
            // InternalCal.g:528:2: () otherlv_1= '(' ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )? otherlv_5= ')' ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )? otherlv_9= '==>' ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )? otherlv_13= ':' ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )* ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )? ( (lv_priorities_22_0= rulePriority ) )* otherlv_23= 'end'
            {
            // InternalCal.g:528:2: ()
            // InternalCal.g:529:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstActorAccess().getAstActorAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,25,FOLLOW_14); 

                	newLeafNode(otherlv_1, grammarAccess.getAstActorAccess().getLeftParenthesisKeyword_1());
                
            // InternalCal.g:538:1: ( ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=84 && LA9_0<=88)||LA9_0==90||(LA9_0>=92 && LA9_0<=94)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalCal.g:538:2: ( (lv_parameters_2_0= ruleActorParameter ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )*
                    {
                    // InternalCal.g:538:2: ( (lv_parameters_2_0= ruleActorParameter ) )
                    // InternalCal.g:539:1: (lv_parameters_2_0= ruleActorParameter )
                    {
                    // InternalCal.g:539:1: (lv_parameters_2_0= ruleActorParameter )
                    // InternalCal.g:540:3: lv_parameters_2_0= ruleActorParameter
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActorAccess().getParametersActorParameterParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_parameters_2_0=ruleActorParameter();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_2_0, 
                            		"net.sf.orcc.cal.Cal.ActorParameter");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:556:2: (otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==26) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalCal.g:556:4: otherlv_3= ',' ( (lv_parameters_4_0= ruleActorParameter ) )
                    	    {
                    	    otherlv_3=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getAstActorAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // InternalCal.g:560:1: ( (lv_parameters_4_0= ruleActorParameter ) )
                    	    // InternalCal.g:561:1: (lv_parameters_4_0= ruleActorParameter )
                    	    {
                    	    // InternalCal.g:561:1: (lv_parameters_4_0= ruleActorParameter )
                    	    // InternalCal.g:562:3: lv_parameters_4_0= ruleActorParameter
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActorAccess().getParametersActorParameterParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_parameters_4_0=ruleActorParameter();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_4_0, 
                    	            		"net.sf.orcc.cal.Cal.ActorParameter");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_17); 

                	newLeafNode(otherlv_5, grammarAccess.getAstActorAccess().getRightParenthesisKeyword_3());
                
            // InternalCal.g:582:1: ( ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>=84 && LA11_0<=88)||LA11_0==90||(LA11_0>=92 && LA11_0<=94)) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalCal.g:582:2: ( (lv_inputs_6_0= ruleAstPort ) ) (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )*
                    {
                    // InternalCal.g:582:2: ( (lv_inputs_6_0= ruleAstPort ) )
                    // InternalCal.g:583:1: (lv_inputs_6_0= ruleAstPort )
                    {
                    // InternalCal.g:583:1: (lv_inputs_6_0= ruleAstPort )
                    // InternalCal.g:584:3: lv_inputs_6_0= ruleAstPort
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActorAccess().getInputsAstPortParserRuleCall_4_0_0()); 
                    	    
                    pushFollow(FOLLOW_18);
                    lv_inputs_6_0=ruleAstPort();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	        }
                           		add(
                           			current, 
                           			"inputs",
                            		lv_inputs_6_0, 
                            		"net.sf.orcc.cal.Cal.AstPort");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:600:2: (otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==26) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalCal.g:600:4: otherlv_7= ',' ( (lv_inputs_8_0= ruleAstPort ) )
                    	    {
                    	    otherlv_7=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getAstActorAccess().getCommaKeyword_4_1_0());
                    	        
                    	    // InternalCal.g:604:1: ( (lv_inputs_8_0= ruleAstPort ) )
                    	    // InternalCal.g:605:1: (lv_inputs_8_0= ruleAstPort )
                    	    {
                    	    // InternalCal.g:605:1: (lv_inputs_8_0= ruleAstPort )
                    	    // InternalCal.g:606:3: lv_inputs_8_0= ruleAstPort
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActorAccess().getInputsAstPortParserRuleCall_4_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_18);
                    	    lv_inputs_8_0=ruleAstPort();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"inputs",
                    	            		lv_inputs_8_0, 
                    	            		"net.sf.orcc.cal.Cal.AstPort");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,28,FOLLOW_19); 

                	newLeafNode(otherlv_9, grammarAccess.getAstActorAccess().getEqualsSignEqualsSignGreaterThanSignKeyword_5());
                
            // InternalCal.g:626:1: ( ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=84 && LA13_0<=88)||LA13_0==90||(LA13_0>=92 && LA13_0<=94)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalCal.g:626:2: ( (lv_outputs_10_0= ruleAstPort ) ) (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )*
                    {
                    // InternalCal.g:626:2: ( (lv_outputs_10_0= ruleAstPort ) )
                    // InternalCal.g:627:1: (lv_outputs_10_0= ruleAstPort )
                    {
                    // InternalCal.g:627:1: (lv_outputs_10_0= ruleAstPort )
                    // InternalCal.g:628:3: lv_outputs_10_0= ruleAstPort
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActorAccess().getOutputsAstPortParserRuleCall_6_0_0()); 
                    	    
                    pushFollow(FOLLOW_20);
                    lv_outputs_10_0=ruleAstPort();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	        }
                           		add(
                           			current, 
                           			"outputs",
                            		lv_outputs_10_0, 
                            		"net.sf.orcc.cal.Cal.AstPort");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:644:2: (otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==26) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalCal.g:644:4: otherlv_11= ',' ( (lv_outputs_12_0= ruleAstPort ) )
                    	    {
                    	    otherlv_11=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_11, grammarAccess.getAstActorAccess().getCommaKeyword_6_1_0());
                    	        
                    	    // InternalCal.g:648:1: ( (lv_outputs_12_0= ruleAstPort ) )
                    	    // InternalCal.g:649:1: (lv_outputs_12_0= ruleAstPort )
                    	    {
                    	    // InternalCal.g:649:1: (lv_outputs_12_0= ruleAstPort )
                    	    // InternalCal.g:650:3: lv_outputs_12_0= ruleAstPort
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActorAccess().getOutputsAstPortParserRuleCall_6_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_20);
                    	    lv_outputs_12_0=ruleAstPort();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"outputs",
                    	            		lv_outputs_12_0, 
                    	            		"net.sf.orcc.cal.Cal.AstPort");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_13=(Token)match(input,22,FOLLOW_21); 

                	newLeafNode(otherlv_13, grammarAccess.getAstActorAccess().getColonKeyword_7());
                
            // InternalCal.g:670:1: ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )*
            loop14:
            do {
                int alt14=7;
                alt14 = dfa14.predict(input);
                switch (alt14) {
            	case 1 :
            	    // InternalCal.g:670:2: ( (lv_functions_14_0= ruleFunction ) )
            	    {
            	    // InternalCal.g:670:2: ( (lv_functions_14_0= ruleFunction ) )
            	    // InternalCal.g:671:1: (lv_functions_14_0= ruleFunction )
            	    {
            	    // InternalCal.g:671:1: (lv_functions_14_0= ruleFunction )
            	    // InternalCal.g:672:3: lv_functions_14_0= ruleFunction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getFunctionsFunctionParserRuleCall_8_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_functions_14_0=ruleFunction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"functions",
            	            		lv_functions_14_0, 
            	            		"net.sf.orcc.cal.Cal.Function");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalCal.g:689:6: ( (lv_procedures_15_0= ruleAstProcedure ) )
            	    {
            	    // InternalCal.g:689:6: ( (lv_procedures_15_0= ruleAstProcedure ) )
            	    // InternalCal.g:690:1: (lv_procedures_15_0= ruleAstProcedure )
            	    {
            	    // InternalCal.g:690:1: (lv_procedures_15_0= ruleAstProcedure )
            	    // InternalCal.g:691:3: lv_procedures_15_0= ruleAstProcedure
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getProceduresAstProcedureParserRuleCall_8_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_procedures_15_0=ruleAstProcedure();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"procedures",
            	            		lv_procedures_15_0, 
            	            		"net.sf.orcc.cal.Cal.AstProcedure");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalCal.g:708:6: ( (lv_actions_16_0= ruleAstAction ) )
            	    {
            	    // InternalCal.g:708:6: ( (lv_actions_16_0= ruleAstAction ) )
            	    // InternalCal.g:709:1: (lv_actions_16_0= ruleAstAction )
            	    {
            	    // InternalCal.g:709:1: (lv_actions_16_0= ruleAstAction )
            	    // InternalCal.g:710:3: lv_actions_16_0= ruleAstAction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getActionsAstActionParserRuleCall_8_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_actions_16_0=ruleAstAction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"actions",
            	            		lv_actions_16_0, 
            	            		"net.sf.orcc.cal.Cal.AstAction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalCal.g:727:6: ( (lv_initializes_17_0= ruleInitialize ) )
            	    {
            	    // InternalCal.g:727:6: ( (lv_initializes_17_0= ruleInitialize ) )
            	    // InternalCal.g:728:1: (lv_initializes_17_0= ruleInitialize )
            	    {
            	    // InternalCal.g:728:1: (lv_initializes_17_0= ruleInitialize )
            	    // InternalCal.g:729:3: lv_initializes_17_0= ruleInitialize
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getInitializesInitializeParserRuleCall_8_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_initializes_17_0=ruleInitialize();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"initializes",
            	            		lv_initializes_17_0, 
            	            		"net.sf.orcc.cal.Cal.Initialize");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalCal.g:746:6: ( (lv_stateVariables_18_0= ruleStateVariable ) )
            	    {
            	    // InternalCal.g:746:6: ( (lv_stateVariables_18_0= ruleStateVariable ) )
            	    // InternalCal.g:747:1: (lv_stateVariables_18_0= ruleStateVariable )
            	    {
            	    // InternalCal.g:747:1: (lv_stateVariables_18_0= ruleStateVariable )
            	    // InternalCal.g:748:3: lv_stateVariables_18_0= ruleStateVariable
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getStateVariablesStateVariableParserRuleCall_8_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_stateVariables_18_0=ruleStateVariable();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"stateVariables",
            	            		lv_stateVariables_18_0, 
            	            		"net.sf.orcc.cal.Cal.StateVariable");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalCal.g:765:6: ( (lv_localFsms_19_0= ruleLocalFsm ) )
            	    {
            	    // InternalCal.g:765:6: ( (lv_localFsms_19_0= ruleLocalFsm ) )
            	    // InternalCal.g:766:1: (lv_localFsms_19_0= ruleLocalFsm )
            	    {
            	    // InternalCal.g:766:1: (lv_localFsms_19_0= ruleLocalFsm )
            	    // InternalCal.g:767:3: lv_localFsms_19_0= ruleLocalFsm
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getLocalFsmsLocalFsmParserRuleCall_8_5_0()); 
            	    	    
            	    pushFollow(FOLLOW_21);
            	    lv_localFsms_19_0=ruleLocalFsm();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"localFsms",
            	            		lv_localFsms_19_0, 
            	            		"net.sf.orcc.cal.Cal.LocalFsm");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // InternalCal.g:783:4: ( ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) ) | ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) ) )?
            int alt15=3;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==36) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==39) ) {
                    alt15=2;
                }
                else if ( (LA15_1==37) ) {
                    alt15=1;
                }
            }
            switch (alt15) {
                case 1 :
                    // InternalCal.g:783:5: ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) )
                    {
                    // InternalCal.g:783:5: ( (lv_scheduleFsm_20_0= ruleScheduleFsm ) )
                    // InternalCal.g:784:1: (lv_scheduleFsm_20_0= ruleScheduleFsm )
                    {
                    // InternalCal.g:784:1: (lv_scheduleFsm_20_0= ruleScheduleFsm )
                    // InternalCal.g:785:3: lv_scheduleFsm_20_0= ruleScheduleFsm
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActorAccess().getScheduleFsmScheduleFsmParserRuleCall_9_0_0()); 
                    	    
                    pushFollow(FOLLOW_22);
                    lv_scheduleFsm_20_0=ruleScheduleFsm();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	        }
                           		set(
                           			current, 
                           			"scheduleFsm",
                            		lv_scheduleFsm_20_0, 
                            		"net.sf.orcc.cal.Cal.ScheduleFsm");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalCal.g:802:6: ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) )
                    {
                    // InternalCal.g:802:6: ( (lv_scheduleRegExp_21_0= ruleScheduleRegExp ) )
                    // InternalCal.g:803:1: (lv_scheduleRegExp_21_0= ruleScheduleRegExp )
                    {
                    // InternalCal.g:803:1: (lv_scheduleRegExp_21_0= ruleScheduleRegExp )
                    // InternalCal.g:804:3: lv_scheduleRegExp_21_0= ruleScheduleRegExp
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActorAccess().getScheduleRegExpScheduleRegExpParserRuleCall_9_1_0()); 
                    	    
                    pushFollow(FOLLOW_22);
                    lv_scheduleRegExp_21_0=ruleScheduleRegExp();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
                    	        }
                           		set(
                           			current, 
                           			"scheduleRegExp",
                            		lv_scheduleRegExp_21_0, 
                            		"net.sf.orcc.cal.Cal.ScheduleRegExp");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // InternalCal.g:820:4: ( (lv_priorities_22_0= rulePriority ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==35) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalCal.g:821:1: (lv_priorities_22_0= rulePriority )
            	    {
            	    // InternalCal.g:821:1: (lv_priorities_22_0= rulePriority )
            	    // InternalCal.g:822:3: lv_priorities_22_0= rulePriority
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActorAccess().getPrioritiesPriorityParserRuleCall_10_0()); 
            	    	    
            	    pushFollow(FOLLOW_22);
            	    lv_priorities_22_0=rulePriority();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActorRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"priorities",
            	            		lv_priorities_22_0, 
            	            		"net.sf.orcc.cal.Cal.Priority");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            otherlv_23=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_23, grammarAccess.getAstActorAccess().getEndKeyword_11());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstActor"


    // $ANTLR start "entryRuleAstPort"
    // InternalCal.g:850:1: entryRuleAstPort returns [EObject current=null] : iv_ruleAstPort= ruleAstPort EOF ;
    public final EObject entryRuleAstPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstPort = null;


        try {
            // InternalCal.g:851:2: (iv_ruleAstPort= ruleAstPort EOF )
            // InternalCal.g:852:2: iv_ruleAstPort= ruleAstPort EOF
            {
             newCompositeNode(grammarAccess.getAstPortRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstPort=ruleAstPort();

            state._fsp--;

             current =iv_ruleAstPort; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstPort"


    // $ANTLR start "ruleAstPort"
    // InternalCal.g:859:1: ruleAstPort returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) ) ;
    public final EObject ruleAstPort() throws RecognitionException {
        EObject current = null;

        Token lv_name_2_0=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_type_1_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:862:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) ) )
            // InternalCal.g:863:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) )
            {
            // InternalCal.g:863:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) )
            // InternalCal.g:863:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) )
            {
            // InternalCal.g:863:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==94) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalCal.g:864:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:864:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:865:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstPortAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_16);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstPortRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // InternalCal.g:881:3: ( (lv_type_1_0= ruleAstType ) )
            // InternalCal.g:882:1: (lv_type_1_0= ruleAstType )
            {
            // InternalCal.g:882:1: (lv_type_1_0= ruleAstType )
            // InternalCal.g:883:3: lv_type_1_0= ruleAstType
            {
             
            	        newCompositeNode(grammarAccess.getAstPortAccess().getTypeAstTypeParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_3);
            lv_type_1_0=ruleAstType();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAstPortRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_1_0, 
                    		"net.sf.orcc.cal.Cal.AstType");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:899:2: ( (lv_name_2_0= RULE_ID ) )
            // InternalCal.g:900:1: (lv_name_2_0= RULE_ID )
            {
            // InternalCal.g:900:1: (lv_name_2_0= RULE_ID )
            // InternalCal.g:901:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            			newLeafNode(lv_name_2_0, grammarAccess.getAstPortAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAstPortRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstPort"


    // $ANTLR start "entryRuleFunction"
    // InternalCal.g:925:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalCal.g:926:2: (iv_ruleFunction= ruleFunction EOF )
            // InternalCal.g:927:2: iv_ruleFunction= ruleFunction EOF
            {
             newCompositeNode(grammarAccess.getFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFunction=ruleFunction();

            state._fsp--;

             current =iv_ruleFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunction"


    // $ANTLR start "ruleFunction"
    // InternalCal.g:934:1: ruleFunction returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' otherlv_8= '-->' ( (lv_type_9_0= ruleAstType ) ) ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )? otherlv_16= 'end' ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_type_9_0 = null;

        EObject lv_variables_11_0 = null;

        EObject lv_variables_13_0 = null;

        EObject lv_expression_15_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:937:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' otherlv_8= '-->' ( (lv_type_9_0= ruleAstType ) ) ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )? otherlv_16= 'end' ) )
            // InternalCal.g:938:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' otherlv_8= '-->' ( (lv_type_9_0= ruleAstType ) ) ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )? otherlv_16= 'end' )
            {
            // InternalCal.g:938:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' otherlv_8= '-->' ( (lv_type_9_0= ruleAstType ) ) ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )? otherlv_16= 'end' )
            // InternalCal.g:938:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' otherlv_8= '-->' ( (lv_type_9_0= ruleAstType ) ) ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )? otherlv_16= 'end'
            {
            // InternalCal.g:938:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==94) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalCal.g:939:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:939:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:940:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_23);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getFunctionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            otherlv_1=(Token)match(input,29,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getFunctionKeyword_1());
                
            // InternalCal.g:960:1: ( (lv_name_2_0= RULE_ID ) )
            // InternalCal.g:961:1: (lv_name_2_0= RULE_ID )
            {
            // InternalCal.g:961:1: (lv_name_2_0= RULE_ID )
            // InternalCal.g:962:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            			newLeafNode(lv_name_2_0, grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFunctionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,25,FOLLOW_14); 

                	newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_3());
                
            // InternalCal.g:982:1: ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=84 && LA20_0<=88)||LA20_0==90||(LA20_0>=92 && LA20_0<=94)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalCal.g:982:2: ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )*
                    {
                    // InternalCal.g:982:2: ( (lv_parameters_4_0= ruleVariableDeclaration ) )
                    // InternalCal.g:983:1: (lv_parameters_4_0= ruleVariableDeclaration )
                    {
                    // InternalCal.g:983:1: (lv_parameters_4_0= ruleVariableDeclaration )
                    // InternalCal.g:984:3: lv_parameters_4_0= ruleVariableDeclaration
                    {
                     
                    	        newCompositeNode(grammarAccess.getFunctionAccess().getParametersVariableDeclarationParserRuleCall_4_0_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_parameters_4_0=ruleVariableDeclaration();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_4_0, 
                            		"net.sf.orcc.cal.Cal.VariableDeclaration");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:1000:2: (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==26) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // InternalCal.g:1000:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) )
                    	    {
                    	    otherlv_5=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getCommaKeyword_4_1_0());
                    	        
                    	    // InternalCal.g:1004:1: ( (lv_parameters_6_0= ruleVariableDeclaration ) )
                    	    // InternalCal.g:1005:1: (lv_parameters_6_0= ruleVariableDeclaration )
                    	    {
                    	    // InternalCal.g:1005:1: (lv_parameters_6_0= ruleVariableDeclaration )
                    	    // InternalCal.g:1006:3: lv_parameters_6_0= ruleVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFunctionAccess().getParametersVariableDeclarationParserRuleCall_4_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_parameters_6_0=ruleVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_6_0, 
                    	            		"net.sf.orcc.cal.Cal.VariableDeclaration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,27,FOLLOW_24); 

                	newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_5());
                
            otherlv_8=(Token)match(input,30,FOLLOW_16); 

                	newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getHyphenMinusHyphenMinusGreaterThanSignKeyword_6());
                
            // InternalCal.g:1030:1: ( (lv_type_9_0= ruleAstType ) )
            // InternalCal.g:1031:1: (lv_type_9_0= ruleAstType )
            {
            // InternalCal.g:1031:1: (lv_type_9_0= ruleAstType )
            // InternalCal.g:1032:3: lv_type_9_0= ruleAstType
            {
             
            	        newCompositeNode(grammarAccess.getFunctionAccess().getTypeAstTypeParserRuleCall_7_0()); 
            	    
            pushFollow(FOLLOW_25);
            lv_type_9_0=ruleAstType();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFunctionRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_9_0, 
                    		"net.sf.orcc.cal.Cal.AstType");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:1048:2: ( (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==22||LA23_0==31) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalCal.g:1048:3: (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_14= ':' ( (lv_expression_15_0= ruleAstExpression ) )
                    {
                    // InternalCal.g:1048:3: (otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )* )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==31) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalCal.g:1048:5: otherlv_10= 'var' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )*
                            {
                            otherlv_10=(Token)match(input,31,FOLLOW_16); 

                                	newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getVarKeyword_8_0_0());
                                
                            // InternalCal.g:1052:1: ( (lv_variables_11_0= ruleValuedVariableDeclaration ) )
                            // InternalCal.g:1053:1: (lv_variables_11_0= ruleValuedVariableDeclaration )
                            {
                            // InternalCal.g:1053:1: (lv_variables_11_0= ruleValuedVariableDeclaration )
                            // InternalCal.g:1054:3: lv_variables_11_0= ruleValuedVariableDeclaration
                            {
                             
                            	        newCompositeNode(grammarAccess.getFunctionAccess().getVariablesValuedVariableDeclarationParserRuleCall_8_0_1_0()); 
                            	    
                            pushFollow(FOLLOW_20);
                            lv_variables_11_0=ruleValuedVariableDeclaration();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFunctionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"variables",
                                    		lv_variables_11_0, 
                                    		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // InternalCal.g:1070:2: (otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) ) )*
                            loop21:
                            do {
                                int alt21=2;
                                int LA21_0 = input.LA(1);

                                if ( (LA21_0==26) ) {
                                    alt21=1;
                                }


                                switch (alt21) {
                            	case 1 :
                            	    // InternalCal.g:1070:4: otherlv_12= ',' ( (lv_variables_13_0= ruleValuedVariableDeclaration ) )
                            	    {
                            	    otherlv_12=(Token)match(input,26,FOLLOW_16); 

                            	        	newLeafNode(otherlv_12, grammarAccess.getFunctionAccess().getCommaKeyword_8_0_2_0());
                            	        
                            	    // InternalCal.g:1074:1: ( (lv_variables_13_0= ruleValuedVariableDeclaration ) )
                            	    // InternalCal.g:1075:1: (lv_variables_13_0= ruleValuedVariableDeclaration )
                            	    {
                            	    // InternalCal.g:1075:1: (lv_variables_13_0= ruleValuedVariableDeclaration )
                            	    // InternalCal.g:1076:3: lv_variables_13_0= ruleValuedVariableDeclaration
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getFunctionAccess().getVariablesValuedVariableDeclarationParserRuleCall_8_0_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_20);
                            	    lv_variables_13_0=ruleValuedVariableDeclaration();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getFunctionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"variables",
                            	            		lv_variables_13_0, 
                            	            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop21;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_14=(Token)match(input,22,FOLLOW_13); 

                        	newLeafNode(otherlv_14, grammarAccess.getFunctionAccess().getColonKeyword_8_1());
                        
                    // InternalCal.g:1096:1: ( (lv_expression_15_0= ruleAstExpression ) )
                    // InternalCal.g:1097:1: (lv_expression_15_0= ruleAstExpression )
                    {
                    // InternalCal.g:1097:1: (lv_expression_15_0= ruleAstExpression )
                    // InternalCal.g:1098:3: lv_expression_15_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getFunctionAccess().getExpressionAstExpressionParserRuleCall_8_2_0()); 
                    	    
                    pushFollow(FOLLOW_26);
                    lv_expression_15_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	        }
                           		set(
                           			current, 
                           			"expression",
                            		lv_expression_15_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_16=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_16, grammarAccess.getFunctionAccess().getEndKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunction"


    // $ANTLR start "entryRuleAstProcedure"
    // InternalCal.g:1126:1: entryRuleAstProcedure returns [EObject current=null] : iv_ruleAstProcedure= ruleAstProcedure EOF ;
    public final EObject entryRuleAstProcedure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstProcedure = null;


        try {
            // InternalCal.g:1127:2: (iv_ruleAstProcedure= ruleAstProcedure EOF )
            // InternalCal.g:1128:2: iv_ruleAstProcedure= ruleAstProcedure EOF
            {
             newCompositeNode(grammarAccess.getAstProcedureRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstProcedure=ruleAstProcedure();

            state._fsp--;

             current =iv_ruleAstProcedure; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstProcedure"


    // $ANTLR start "ruleAstProcedure"
    // InternalCal.g:1135:1: ruleAstProcedure returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'procedure' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )? otherlv_14= 'end' ) ;
    public final EObject ruleAstProcedure() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_variables_9_0 = null;

        EObject lv_variables_11_0 = null;

        EObject lv_statements_13_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1138:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'procedure' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )? otherlv_14= 'end' ) )
            // InternalCal.g:1139:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'procedure' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )? otherlv_14= 'end' )
            {
            // InternalCal.g:1139:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'procedure' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )? otherlv_14= 'end' )
            // InternalCal.g:1139:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'procedure' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )? otherlv_7= ')' ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )? otherlv_14= 'end'
            {
            // InternalCal.g:1139:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==94) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalCal.g:1140:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:1140:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:1141:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstProcedureAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_27);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            otherlv_1=(Token)match(input,32,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getAstProcedureAccess().getProcedureKeyword_1());
                
            // InternalCal.g:1161:1: ( (lv_name_2_0= RULE_ID ) )
            // InternalCal.g:1162:1: (lv_name_2_0= RULE_ID )
            {
            // InternalCal.g:1162:1: (lv_name_2_0= RULE_ID )
            // InternalCal.g:1163:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            			newLeafNode(lv_name_2_0, grammarAccess.getAstProcedureAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAstProcedureRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,25,FOLLOW_14); 

                	newLeafNode(otherlv_3, grammarAccess.getAstProcedureAccess().getLeftParenthesisKeyword_3());
                
            // InternalCal.g:1183:1: ( ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )* )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=84 && LA26_0<=88)||LA26_0==90||(LA26_0>=92 && LA26_0<=94)) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalCal.g:1183:2: ( (lv_parameters_4_0= ruleVariableDeclaration ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )*
                    {
                    // InternalCal.g:1183:2: ( (lv_parameters_4_0= ruleVariableDeclaration ) )
                    // InternalCal.g:1184:1: (lv_parameters_4_0= ruleVariableDeclaration )
                    {
                    // InternalCal.g:1184:1: (lv_parameters_4_0= ruleVariableDeclaration )
                    // InternalCal.g:1185:3: lv_parameters_4_0= ruleVariableDeclaration
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstProcedureAccess().getParametersVariableDeclarationParserRuleCall_4_0_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_parameters_4_0=ruleVariableDeclaration();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_4_0, 
                            		"net.sf.orcc.cal.Cal.VariableDeclaration");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:1201:2: (otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==26) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalCal.g:1201:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleVariableDeclaration ) )
                    	    {
                    	    otherlv_5=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getAstProcedureAccess().getCommaKeyword_4_1_0());
                    	        
                    	    // InternalCal.g:1205:1: ( (lv_parameters_6_0= ruleVariableDeclaration ) )
                    	    // InternalCal.g:1206:1: (lv_parameters_6_0= ruleVariableDeclaration )
                    	    {
                    	    // InternalCal.g:1206:1: (lv_parameters_6_0= ruleVariableDeclaration )
                    	    // InternalCal.g:1207:3: lv_parameters_6_0= ruleVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstProcedureAccess().getParametersVariableDeclarationParserRuleCall_4_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_parameters_6_0=ruleVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_6_0, 
                    	            		"net.sf.orcc.cal.Cal.VariableDeclaration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,27,FOLLOW_28); 

                	newLeafNode(otherlv_7, grammarAccess.getAstProcedureAccess().getRightParenthesisKeyword_5());
                
            // InternalCal.g:1227:1: ( (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )* )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==31||LA30_0==33) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalCal.g:1227:2: (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )? otherlv_12= 'begin' ( (lv_statements_13_0= ruleStatement ) )*
                    {
                    // InternalCal.g:1227:2: (otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )* )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==31) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalCal.g:1227:4: otherlv_8= 'var' ( (lv_variables_9_0= ruleValuedVariableDeclaration ) ) (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )*
                            {
                            otherlv_8=(Token)match(input,31,FOLLOW_16); 

                                	newLeafNode(otherlv_8, grammarAccess.getAstProcedureAccess().getVarKeyword_6_0_0());
                                
                            // InternalCal.g:1231:1: ( (lv_variables_9_0= ruleValuedVariableDeclaration ) )
                            // InternalCal.g:1232:1: (lv_variables_9_0= ruleValuedVariableDeclaration )
                            {
                            // InternalCal.g:1232:1: (lv_variables_9_0= ruleValuedVariableDeclaration )
                            // InternalCal.g:1233:3: lv_variables_9_0= ruleValuedVariableDeclaration
                            {
                             
                            	        newCompositeNode(grammarAccess.getAstProcedureAccess().getVariablesValuedVariableDeclarationParserRuleCall_6_0_1_0()); 
                            	    
                            pushFollow(FOLLOW_29);
                            lv_variables_9_0=ruleValuedVariableDeclaration();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"variables",
                                    		lv_variables_9_0, 
                                    		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // InternalCal.g:1249:2: (otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) ) )*
                            loop27:
                            do {
                                int alt27=2;
                                int LA27_0 = input.LA(1);

                                if ( (LA27_0==26) ) {
                                    alt27=1;
                                }


                                switch (alt27) {
                            	case 1 :
                            	    // InternalCal.g:1249:4: otherlv_10= ',' ( (lv_variables_11_0= ruleValuedVariableDeclaration ) )
                            	    {
                            	    otherlv_10=(Token)match(input,26,FOLLOW_16); 

                            	        	newLeafNode(otherlv_10, grammarAccess.getAstProcedureAccess().getCommaKeyword_6_0_2_0());
                            	        
                            	    // InternalCal.g:1253:1: ( (lv_variables_11_0= ruleValuedVariableDeclaration ) )
                            	    // InternalCal.g:1254:1: (lv_variables_11_0= ruleValuedVariableDeclaration )
                            	    {
                            	    // InternalCal.g:1254:1: (lv_variables_11_0= ruleValuedVariableDeclaration )
                            	    // InternalCal.g:1255:3: lv_variables_11_0= ruleValuedVariableDeclaration
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getAstProcedureAccess().getVariablesValuedVariableDeclarationParserRuleCall_6_0_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_29);
                            	    lv_variables_11_0=ruleValuedVariableDeclaration();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"variables",
                            	            		lv_variables_11_0, 
                            	            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop27;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,33,FOLLOW_30); 

                        	newLeafNode(otherlv_12, grammarAccess.getAstProcedureAccess().getBeginKeyword_6_1());
                        
                    // InternalCal.g:1275:1: ( (lv_statements_13_0= ruleStatement ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==RULE_ID||LA29_0==52||LA29_0==55||LA29_0==59||LA29_0==94) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // InternalCal.g:1276:1: (lv_statements_13_0= ruleStatement )
                    	    {
                    	    // InternalCal.g:1276:1: (lv_statements_13_0= ruleStatement )
                    	    // InternalCal.g:1277:3: lv_statements_13_0= ruleStatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstProcedureAccess().getStatementsStatementParserRuleCall_6_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_30);
                    	    lv_statements_13_0=ruleStatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstProcedureRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"statements",
                    	            		lv_statements_13_0, 
                    	            		"net.sf.orcc.cal.Cal.Statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_14=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_14, grammarAccess.getAstProcedureAccess().getEndKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstProcedure"


    // $ANTLR start "entryRuleActorParameter"
    // InternalCal.g:1305:1: entryRuleActorParameter returns [EObject current=null] : iv_ruleActorParameter= ruleActorParameter EOF ;
    public final EObject entryRuleActorParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActorParameter = null;


        try {
            // InternalCal.g:1306:2: (iv_ruleActorParameter= ruleActorParameter EOF )
            // InternalCal.g:1307:2: iv_ruleActorParameter= ruleActorParameter EOF
            {
             newCompositeNode(grammarAccess.getActorParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleActorParameter=ruleActorParameter();

            state._fsp--;

             current =iv_ruleActorParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActorParameter"


    // $ANTLR start "ruleActorParameter"
    // InternalCal.g:1314:1: ruleActorParameter returns [EObject current=null] : (this_VariableDeclaration_0= ruleVariableDeclaration (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )? ) ;
    public final EObject ruleActorParameter() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_VariableDeclaration_0 = null;

        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1317:28: ( (this_VariableDeclaration_0= ruleVariableDeclaration (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )? ) )
            // InternalCal.g:1318:1: (this_VariableDeclaration_0= ruleVariableDeclaration (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )? )
            {
            // InternalCal.g:1318:1: (this_VariableDeclaration_0= ruleVariableDeclaration (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )? )
            // InternalCal.g:1319:5: this_VariableDeclaration_0= ruleVariableDeclaration (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )?
            {
             
                    newCompositeNode(grammarAccess.getActorParameterAccess().getVariableDeclarationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_31);
            this_VariableDeclaration_0=ruleVariableDeclaration();

            state._fsp--;

             
                    current = this_VariableDeclaration_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:1327:1: (otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==24) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalCal.g:1327:3: otherlv_1= '=' ( (lv_value_2_0= ruleAstExpression ) )
                    {
                    otherlv_1=(Token)match(input,24,FOLLOW_13); 

                        	newLeafNode(otherlv_1, grammarAccess.getActorParameterAccess().getEqualsSignKeyword_1_0());
                        
                    // InternalCal.g:1331:1: ( (lv_value_2_0= ruleAstExpression ) )
                    // InternalCal.g:1332:1: (lv_value_2_0= ruleAstExpression )
                    {
                    // InternalCal.g:1332:1: (lv_value_2_0= ruleAstExpression )
                    // InternalCal.g:1333:3: lv_value_2_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getActorParameterAccess().getValueAstExpressionParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_value_2_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getActorParameterRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActorParameter"


    // $ANTLR start "entryRuleStateVariable"
    // InternalCal.g:1357:1: entryRuleStateVariable returns [EObject current=null] : iv_ruleStateVariable= ruleStateVariable EOF ;
    public final EObject entryRuleStateVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateVariable = null;


        try {
            // InternalCal.g:1358:2: (iv_ruleStateVariable= ruleStateVariable EOF )
            // InternalCal.g:1359:2: iv_ruleStateVariable= ruleStateVariable EOF
            {
             newCompositeNode(grammarAccess.getStateVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateVariable=ruleStateVariable();

            state._fsp--;

             current =iv_ruleStateVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateVariable"


    // $ANTLR start "ruleStateVariable"
    // InternalCal.g:1366:1: ruleStateVariable returns [EObject current=null] : (this_ValuedVariableDeclaration_0= ruleValuedVariableDeclaration otherlv_1= ';' ) ;
    public final EObject ruleStateVariable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_ValuedVariableDeclaration_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1369:28: ( (this_ValuedVariableDeclaration_0= ruleValuedVariableDeclaration otherlv_1= ';' ) )
            // InternalCal.g:1370:1: (this_ValuedVariableDeclaration_0= ruleValuedVariableDeclaration otherlv_1= ';' )
            {
            // InternalCal.g:1370:1: (this_ValuedVariableDeclaration_0= ruleValuedVariableDeclaration otherlv_1= ';' )
            // InternalCal.g:1371:5: this_ValuedVariableDeclaration_0= ruleValuedVariableDeclaration otherlv_1= ';'
            {
             
                    newCompositeNode(grammarAccess.getStateVariableAccess().getValuedVariableDeclarationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_4);
            this_ValuedVariableDeclaration_0=ruleValuedVariableDeclaration();

            state._fsp--;

             
                    current = this_ValuedVariableDeclaration_0; 
                    afterParserOrEnumRuleCall();
                
            otherlv_1=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getStateVariableAccess().getSemicolonKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateVariable"


    // $ANTLR start "entryRuleAstTag"
    // InternalCal.g:1391:1: entryRuleAstTag returns [EObject current=null] : iv_ruleAstTag= ruleAstTag EOF ;
    public final EObject entryRuleAstTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTag = null;


        try {
            // InternalCal.g:1392:2: (iv_ruleAstTag= ruleAstTag EOF )
            // InternalCal.g:1393:2: iv_ruleAstTag= ruleAstTag EOF
            {
             newCompositeNode(grammarAccess.getAstTagRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTag=ruleAstTag();

            state._fsp--;

             current =iv_ruleAstTag; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTag"


    // $ANTLR start "ruleAstTag"
    // InternalCal.g:1400:1: ruleAstTag returns [EObject current=null] : ( ( (lv_identifiers_0_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )* ) ;
    public final EObject ruleAstTag() throws RecognitionException {
        EObject current = null;

        Token lv_identifiers_0_0=null;
        Token otherlv_1=null;
        Token lv_identifiers_2_0=null;

         enterRule(); 
            
        try {
            // InternalCal.g:1403:28: ( ( ( (lv_identifiers_0_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )* ) )
            // InternalCal.g:1404:1: ( ( (lv_identifiers_0_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )* )
            {
            // InternalCal.g:1404:1: ( ( (lv_identifiers_0_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )* )
            // InternalCal.g:1404:2: ( (lv_identifiers_0_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )*
            {
            // InternalCal.g:1404:2: ( (lv_identifiers_0_0= RULE_ID ) )
            // InternalCal.g:1405:1: (lv_identifiers_0_0= RULE_ID )
            {
            // InternalCal.g:1405:1: (lv_identifiers_0_0= RULE_ID )
            // InternalCal.g:1406:3: lv_identifiers_0_0= RULE_ID
            {
            lv_identifiers_0_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            			newLeafNode(lv_identifiers_0_0, grammarAccess.getAstTagAccess().getIdentifiersIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAstTagRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"identifiers",
                    		lv_identifiers_0_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            // InternalCal.g:1422:2: (otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==20) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalCal.g:1422:4: otherlv_1= '.' ( (lv_identifiers_2_0= RULE_ID ) )
            	    {
            	    otherlv_1=(Token)match(input,20,FOLLOW_3); 

            	        	newLeafNode(otherlv_1, grammarAccess.getAstTagAccess().getFullStopKeyword_1_0());
            	        
            	    // InternalCal.g:1426:1: ( (lv_identifiers_2_0= RULE_ID ) )
            	    // InternalCal.g:1427:1: (lv_identifiers_2_0= RULE_ID )
            	    {
            	    // InternalCal.g:1427:1: (lv_identifiers_2_0= RULE_ID )
            	    // InternalCal.g:1428:3: lv_identifiers_2_0= RULE_ID
            	    {
            	    lv_identifiers_2_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            	    			newLeafNode(lv_identifiers_2_0, grammarAccess.getAstTagAccess().getIdentifiersIDTerminalRuleCall_1_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getAstTagRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"identifiers",
            	            		lv_identifiers_2_0, 
            	            		"org.eclipse.xtext.common.Terminals.ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTag"


    // $ANTLR start "entryRuleInequality"
    // InternalCal.g:1452:1: entryRuleInequality returns [EObject current=null] : iv_ruleInequality= ruleInequality EOF ;
    public final EObject entryRuleInequality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInequality = null;


        try {
            // InternalCal.g:1453:2: (iv_ruleInequality= ruleInequality EOF )
            // InternalCal.g:1454:2: iv_ruleInequality= ruleInequality EOF
            {
             newCompositeNode(grammarAccess.getInequalityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInequality=ruleInequality();

            state._fsp--;

             current =iv_ruleInequality; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInequality"


    // $ANTLR start "ruleInequality"
    // InternalCal.g:1461:1: ruleInequality returns [EObject current=null] : ( ( (lv_tags_0_0= ruleAstTag ) ) (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+ otherlv_3= ';' ) ;
    public final EObject ruleInequality() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_tags_0_0 = null;

        EObject lv_tags_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1464:28: ( ( ( (lv_tags_0_0= ruleAstTag ) ) (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+ otherlv_3= ';' ) )
            // InternalCal.g:1465:1: ( ( (lv_tags_0_0= ruleAstTag ) ) (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+ otherlv_3= ';' )
            {
            // InternalCal.g:1465:1: ( ( (lv_tags_0_0= ruleAstTag ) ) (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+ otherlv_3= ';' )
            // InternalCal.g:1465:2: ( (lv_tags_0_0= ruleAstTag ) ) (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+ otherlv_3= ';'
            {
            // InternalCal.g:1465:2: ( (lv_tags_0_0= ruleAstTag ) )
            // InternalCal.g:1466:1: (lv_tags_0_0= ruleAstTag )
            {
            // InternalCal.g:1466:1: (lv_tags_0_0= ruleAstTag )
            // InternalCal.g:1467:3: lv_tags_0_0= ruleAstTag
            {
             
            	        newCompositeNode(grammarAccess.getInequalityAccess().getTagsAstTagParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_32);
            lv_tags_0_0=ruleAstTag();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getInequalityRule());
            	        }
                   		add(
                   			current, 
                   			"tags",
                    		lv_tags_0_0, 
                    		"net.sf.orcc.cal.Cal.AstTag");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:1483:2: (otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) ) )+
            int cnt33=0;
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==34) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalCal.g:1483:4: otherlv_1= '>' ( (lv_tags_2_0= ruleAstTag ) )
            	    {
            	    otherlv_1=(Token)match(input,34,FOLLOW_3); 

            	        	newLeafNode(otherlv_1, grammarAccess.getInequalityAccess().getGreaterThanSignKeyword_1_0());
            	        
            	    // InternalCal.g:1487:1: ( (lv_tags_2_0= ruleAstTag ) )
            	    // InternalCal.g:1488:1: (lv_tags_2_0= ruleAstTag )
            	    {
            	    // InternalCal.g:1488:1: (lv_tags_2_0= ruleAstTag )
            	    // InternalCal.g:1489:3: lv_tags_2_0= ruleAstTag
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getInequalityAccess().getTagsAstTagParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_33);
            	    lv_tags_2_0=ruleAstTag();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getInequalityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"tags",
            	            		lv_tags_2_0, 
            	            		"net.sf.orcc.cal.Cal.AstTag");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt33 >= 1 ) break loop33;
                        EarlyExitException eee =
                            new EarlyExitException(33, input);
                        throw eee;
                }
                cnt33++;
            } while (true);

            otherlv_3=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_3, grammarAccess.getInequalityAccess().getSemicolonKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInequality"


    // $ANTLR start "entryRulePriority"
    // InternalCal.g:1517:1: entryRulePriority returns [EObject current=null] : iv_rulePriority= rulePriority EOF ;
    public final EObject entryRulePriority() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePriority = null;


        try {
            // InternalCal.g:1518:2: (iv_rulePriority= rulePriority EOF )
            // InternalCal.g:1519:2: iv_rulePriority= rulePriority EOF
            {
             newCompositeNode(grammarAccess.getPriorityRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePriority=rulePriority();

            state._fsp--;

             current =iv_rulePriority; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePriority"


    // $ANTLR start "rulePriority"
    // InternalCal.g:1526:1: rulePriority returns [EObject current=null] : ( () otherlv_1= 'priority' ( (lv_inequalities_2_0= ruleInequality ) )* otherlv_3= 'end' ) ;
    public final EObject rulePriority() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_inequalities_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1529:28: ( ( () otherlv_1= 'priority' ( (lv_inequalities_2_0= ruleInequality ) )* otherlv_3= 'end' ) )
            // InternalCal.g:1530:1: ( () otherlv_1= 'priority' ( (lv_inequalities_2_0= ruleInequality ) )* otherlv_3= 'end' )
            {
            // InternalCal.g:1530:1: ( () otherlv_1= 'priority' ( (lv_inequalities_2_0= ruleInequality ) )* otherlv_3= 'end' )
            // InternalCal.g:1530:2: () otherlv_1= 'priority' ( (lv_inequalities_2_0= ruleInequality ) )* otherlv_3= 'end'
            {
            // InternalCal.g:1530:2: ()
            // InternalCal.g:1531:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getPriorityAccess().getPriorityAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,35,FOLLOW_34); 

                	newLeafNode(otherlv_1, grammarAccess.getPriorityAccess().getPriorityKeyword_1());
                
            // InternalCal.g:1540:1: ( (lv_inequalities_2_0= ruleInequality ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_ID) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalCal.g:1541:1: (lv_inequalities_2_0= ruleInequality )
            	    {
            	    // InternalCal.g:1541:1: (lv_inequalities_2_0= ruleInequality )
            	    // InternalCal.g:1542:3: lv_inequalities_2_0= ruleInequality
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getPriorityAccess().getInequalitiesInequalityParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_34);
            	    lv_inequalities_2_0=ruleInequality();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getPriorityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"inequalities",
            	            		lv_inequalities_2_0, 
            	            		"net.sf.orcc.cal.Cal.Inequality");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            otherlv_3=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_3, grammarAccess.getPriorityAccess().getEndKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePriority"


    // $ANTLR start "entryRuleScheduleFsm"
    // InternalCal.g:1570:1: entryRuleScheduleFsm returns [EObject current=null] : iv_ruleScheduleFsm= ruleScheduleFsm EOF ;
    public final EObject entryRuleScheduleFsm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScheduleFsm = null;


        try {
            // InternalCal.g:1571:2: (iv_ruleScheduleFsm= ruleScheduleFsm EOF )
            // InternalCal.g:1572:2: iv_ruleScheduleFsm= ruleScheduleFsm EOF
            {
             newCompositeNode(grammarAccess.getScheduleFsmRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleScheduleFsm=ruleScheduleFsm();

            state._fsp--;

             current =iv_ruleScheduleFsm; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScheduleFsm"


    // $ANTLR start "ruleScheduleFsm"
    // InternalCal.g:1579:1: ruleScheduleFsm returns [EObject current=null] : (otherlv_0= 'schedule' otherlv_1= 'fsm' ( (otherlv_2= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' ) ;
    public final EObject ruleScheduleFsm() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_contents_4_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1582:28: ( (otherlv_0= 'schedule' otherlv_1= 'fsm' ( (otherlv_2= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' ) )
            // InternalCal.g:1583:1: (otherlv_0= 'schedule' otherlv_1= 'fsm' ( (otherlv_2= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' )
            {
            // InternalCal.g:1583:1: (otherlv_0= 'schedule' otherlv_1= 'fsm' ( (otherlv_2= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' )
            // InternalCal.g:1583:3: otherlv_0= 'schedule' otherlv_1= 'fsm' ( (otherlv_2= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_35); 

                	newLeafNode(otherlv_0, grammarAccess.getScheduleFsmAccess().getScheduleKeyword_0());
                
            otherlv_1=(Token)match(input,37,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getScheduleFsmAccess().getFsmKeyword_1());
                
            // InternalCal.g:1591:1: ( (otherlv_2= RULE_ID ) )
            // InternalCal.g:1592:1: (otherlv_2= RULE_ID )
            {
            // InternalCal.g:1592:1: (otherlv_2= RULE_ID )
            // InternalCal.g:1593:3: otherlv_2= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getScheduleFsmRule());
            	        }
                    
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_8); 

            		newLeafNode(otherlv_2, grammarAccess.getScheduleFsmAccess().getInitialStateAstStateCrossReference_2_0()); 
            	

            }


            }

            otherlv_3=(Token)match(input,22,FOLLOW_34); 

                	newLeafNode(otherlv_3, grammarAccess.getScheduleFsmAccess().getColonKeyword_3());
                
            // InternalCal.g:1608:1: ( (lv_contents_4_0= ruleFsm ) )
            // InternalCal.g:1609:1: (lv_contents_4_0= ruleFsm )
            {
            // InternalCal.g:1609:1: (lv_contents_4_0= ruleFsm )
            // InternalCal.g:1610:3: lv_contents_4_0= ruleFsm
            {
             
            	        newCompositeNode(grammarAccess.getScheduleFsmAccess().getContentsFsmParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_26);
            lv_contents_4_0=ruleFsm();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getScheduleFsmRule());
            	        }
                   		set(
                   			current, 
                   			"contents",
                    		lv_contents_4_0, 
                    		"net.sf.orcc.cal.Cal.Fsm");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_5=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_5, grammarAccess.getScheduleFsmAccess().getEndKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScheduleFsm"


    // $ANTLR start "entryRuleFsm"
    // InternalCal.g:1638:1: entryRuleFsm returns [EObject current=null] : iv_ruleFsm= ruleFsm EOF ;
    public final EObject entryRuleFsm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFsm = null;


        try {
            // InternalCal.g:1639:2: (iv_ruleFsm= ruleFsm EOF )
            // InternalCal.g:1640:2: iv_ruleFsm= ruleFsm EOF
            {
             newCompositeNode(grammarAccess.getFsmRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFsm=ruleFsm();

            state._fsp--;

             current =iv_ruleFsm; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFsm"


    // $ANTLR start "ruleFsm"
    // InternalCal.g:1647:1: ruleFsm returns [EObject current=null] : ( () ( (lv_transitions_1_0= ruleAstTransition ) )* ) ;
    public final EObject ruleFsm() throws RecognitionException {
        EObject current = null;

        EObject lv_transitions_1_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1650:28: ( ( () ( (lv_transitions_1_0= ruleAstTransition ) )* ) )
            // InternalCal.g:1651:1: ( () ( (lv_transitions_1_0= ruleAstTransition ) )* )
            {
            // InternalCal.g:1651:1: ( () ( (lv_transitions_1_0= ruleAstTransition ) )* )
            // InternalCal.g:1651:2: () ( (lv_transitions_1_0= ruleAstTransition ) )*
            {
            // InternalCal.g:1651:2: ()
            // InternalCal.g:1652:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getFsmAccess().getFsmAction_0(),
                        current);
                

            }

            // InternalCal.g:1657:2: ( (lv_transitions_1_0= ruleAstTransition ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_ID) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalCal.g:1658:1: (lv_transitions_1_0= ruleAstTransition )
            	    {
            	    // InternalCal.g:1658:1: (lv_transitions_1_0= ruleAstTransition )
            	    // InternalCal.g:1659:3: lv_transitions_1_0= ruleAstTransition
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getFsmAccess().getTransitionsAstTransitionParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_36);
            	    lv_transitions_1_0=ruleAstTransition();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getFsmRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"transitions",
            	            		lv_transitions_1_0, 
            	            		"net.sf.orcc.cal.Cal.AstTransition");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFsm"


    // $ANTLR start "entryRuleAstTransition"
    // InternalCal.g:1683:1: entryRuleAstTransition returns [EObject current=null] : iv_ruleAstTransition= ruleAstTransition EOF ;
    public final EObject entryRuleAstTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTransition = null;


        try {
            // InternalCal.g:1684:2: (iv_ruleAstTransition= ruleAstTransition EOF )
            // InternalCal.g:1685:2: iv_ruleAstTransition= ruleAstTransition EOF
            {
             newCompositeNode(grammarAccess.getAstTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTransition=ruleAstTransition();

            state._fsp--;

             current =iv_ruleAstTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTransition"


    // $ANTLR start "ruleAstTransition"
    // InternalCal.g:1692:1: ruleAstTransition returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ')' otherlv_4= '-->' ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) ) otherlv_7= ';' ) ;
    public final EObject ruleAstTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_tag_2_0 = null;

        EObject lv_externalTarget_6_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1695:28: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ')' otherlv_4= '-->' ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) ) otherlv_7= ';' ) )
            // InternalCal.g:1696:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ')' otherlv_4= '-->' ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) ) otherlv_7= ';' )
            {
            // InternalCal.g:1696:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ')' otherlv_4= '-->' ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) ) otherlv_7= ';' )
            // InternalCal.g:1696:2: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ')' otherlv_4= '-->' ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) ) otherlv_7= ';'
            {
            // InternalCal.g:1696:2: ( (otherlv_0= RULE_ID ) )
            // InternalCal.g:1697:1: (otherlv_0= RULE_ID )
            {
            // InternalCal.g:1697:1: (otherlv_0= RULE_ID )
            // InternalCal.g:1698:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getAstTransitionRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            		newLeafNode(otherlv_0, grammarAccess.getAstTransitionAccess().getSourceAstStateCrossReference_0_0()); 
            	

            }


            }

            otherlv_1=(Token)match(input,25,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTransitionAccess().getLeftParenthesisKeyword_1());
                
            // InternalCal.g:1713:1: ( (lv_tag_2_0= ruleAstTag ) )
            // InternalCal.g:1714:1: (lv_tag_2_0= ruleAstTag )
            {
            // InternalCal.g:1714:1: (lv_tag_2_0= ruleAstTag )
            // InternalCal.g:1715:3: lv_tag_2_0= ruleAstTag
            {
             
            	        newCompositeNode(grammarAccess.getAstTransitionAccess().getTagAstTagParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_37);
            lv_tag_2_0=ruleAstTag();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAstTransitionRule());
            	        }
                   		set(
                   			current, 
                   			"tag",
                    		lv_tag_2_0, 
                    		"net.sf.orcc.cal.Cal.AstTag");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,27,FOLLOW_24); 

                	newLeafNode(otherlv_3, grammarAccess.getAstTransitionAccess().getRightParenthesisKeyword_3());
                
            otherlv_4=(Token)match(input,30,FOLLOW_3); 

                	newLeafNode(otherlv_4, grammarAccess.getAstTransitionAccess().getHyphenMinusHyphenMinusGreaterThanSignKeyword_4());
                
            // InternalCal.g:1739:1: ( ( (otherlv_5= RULE_ID ) ) | ( (lv_externalTarget_6_0= ruleExternalTarget ) ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_ID) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==20) ) {
                    alt36=2;
                }
                else if ( (LA36_1==16) ) {
                    alt36=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalCal.g:1739:2: ( (otherlv_5= RULE_ID ) )
                    {
                    // InternalCal.g:1739:2: ( (otherlv_5= RULE_ID ) )
                    // InternalCal.g:1740:1: (otherlv_5= RULE_ID )
                    {
                    // InternalCal.g:1740:1: (otherlv_5= RULE_ID )
                    // InternalCal.g:1741:3: otherlv_5= RULE_ID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getAstTransitionRule());
                    	        }
                            
                    otherlv_5=(Token)match(input,RULE_ID,FOLLOW_4); 

                    		newLeafNode(otherlv_5, grammarAccess.getAstTransitionAccess().getTargetAstStateCrossReference_5_0_0()); 
                    	

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalCal.g:1753:6: ( (lv_externalTarget_6_0= ruleExternalTarget ) )
                    {
                    // InternalCal.g:1753:6: ( (lv_externalTarget_6_0= ruleExternalTarget ) )
                    // InternalCal.g:1754:1: (lv_externalTarget_6_0= ruleExternalTarget )
                    {
                    // InternalCal.g:1754:1: (lv_externalTarget_6_0= ruleExternalTarget )
                    // InternalCal.g:1755:3: lv_externalTarget_6_0= ruleExternalTarget
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstTransitionAccess().getExternalTargetExternalTargetParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FOLLOW_4);
                    lv_externalTarget_6_0=ruleExternalTarget();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstTransitionRule());
                    	        }
                           		set(
                           			current, 
                           			"externalTarget",
                            		lv_externalTarget_6_0, 
                            		"net.sf.orcc.cal.Cal.ExternalTarget");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_7, grammarAccess.getAstTransitionAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTransition"


    // $ANTLR start "entryRuleExternalTarget"
    // InternalCal.g:1783:1: entryRuleExternalTarget returns [EObject current=null] : iv_ruleExternalTarget= ruleExternalTarget EOF ;
    public final EObject entryRuleExternalTarget() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalTarget = null;


        try {
            // InternalCal.g:1784:2: (iv_ruleExternalTarget= ruleExternalTarget EOF )
            // InternalCal.g:1785:2: iv_ruleExternalTarget= ruleExternalTarget EOF
            {
             newCompositeNode(grammarAccess.getExternalTargetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalTarget=ruleExternalTarget();

            state._fsp--;

             current =iv_ruleExternalTarget; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalTarget"


    // $ANTLR start "ruleExternalTarget"
    // InternalCal.g:1792:1: ruleExternalTarget returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= ')' ) ;
    public final EObject ruleExternalTarget() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;

         enterRule(); 
            
        try {
            // InternalCal.g:1795:28: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= ')' ) )
            // InternalCal.g:1796:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= ')' )
            {
            // InternalCal.g:1796:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= ')' )
            // InternalCal.g:1796:2: ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= ')'
            {
            // InternalCal.g:1796:2: ( (otherlv_0= RULE_ID ) )
            // InternalCal.g:1797:1: (otherlv_0= RULE_ID )
            {
            // InternalCal.g:1797:1: (otherlv_0= RULE_ID )
            // InternalCal.g:1798:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getExternalTargetRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_38); 

            		newLeafNode(otherlv_0, grammarAccess.getExternalTargetAccess().getFsmLocalFsmCrossReference_0_0()); 
            	

            }


            }

            otherlv_1=(Token)match(input,20,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getExternalTargetAccess().getFullStopKeyword_1());
                
            // InternalCal.g:1813:1: ( (otherlv_2= RULE_ID ) )
            // InternalCal.g:1814:1: (otherlv_2= RULE_ID )
            {
            // InternalCal.g:1814:1: (otherlv_2= RULE_ID )
            // InternalCal.g:1815:3: otherlv_2= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getExternalTargetRule());
            	        }
                    
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_7); 

            		newLeafNode(otherlv_2, grammarAccess.getExternalTargetAccess().getStateAstStateCrossReference_2_0()); 
            	

            }


            }

            otherlv_3=(Token)match(input,25,FOLLOW_3); 

                	newLeafNode(otherlv_3, grammarAccess.getExternalTargetAccess().getLeftParenthesisKeyword_3());
                
            // InternalCal.g:1830:1: ( (otherlv_4= RULE_ID ) )
            // InternalCal.g:1831:1: (otherlv_4= RULE_ID )
            {
            // InternalCal.g:1831:1: (otherlv_4= RULE_ID )
            // InternalCal.g:1832:3: otherlv_4= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getExternalTargetRule());
            	        }
                    
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_39); 

            		newLeafNode(otherlv_4, grammarAccess.getExternalTargetAccess().getFromAstStateCrossReference_4_0()); 
            	

            }


            }

            otherlv_5=(Token)match(input,38,FOLLOW_3); 

                	newLeafNode(otherlv_5, grammarAccess.getExternalTargetAccess().getEqualsSignGreaterThanSignKeyword_5());
                
            // InternalCal.g:1847:1: ( (otherlv_6= RULE_ID ) )
            // InternalCal.g:1848:1: (otherlv_6= RULE_ID )
            {
            // InternalCal.g:1848:1: (otherlv_6= RULE_ID )
            // InternalCal.g:1849:3: otherlv_6= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getExternalTargetRule());
            	        }
                    
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_37); 

            		newLeafNode(otherlv_6, grammarAccess.getExternalTargetAccess().getToAstStateCrossReference_6_0()); 
            	

            }


            }

            otherlv_7=(Token)match(input,27,FOLLOW_2); 

                	newLeafNode(otherlv_7, grammarAccess.getExternalTargetAccess().getRightParenthesisKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalTarget"


    // $ANTLR start "entryRuleScheduleRegExp"
    // InternalCal.g:1874:1: entryRuleScheduleRegExp returns [EObject current=null] : iv_ruleScheduleRegExp= ruleScheduleRegExp EOF ;
    public final EObject entryRuleScheduleRegExp() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScheduleRegExp = null;


        try {
            // InternalCal.g:1875:2: (iv_ruleScheduleRegExp= ruleScheduleRegExp EOF )
            // InternalCal.g:1876:2: iv_ruleScheduleRegExp= ruleScheduleRegExp EOF
            {
             newCompositeNode(grammarAccess.getScheduleRegExpRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleScheduleRegExp=ruleScheduleRegExp();

            state._fsp--;

             current =iv_ruleScheduleRegExp; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScheduleRegExp"


    // $ANTLR start "ruleScheduleRegExp"
    // InternalCal.g:1883:1: ruleScheduleRegExp returns [EObject current=null] : (otherlv_0= 'schedule' otherlv_1= 'regexp' ( (lv_exp_2_0= ruleRegExp ) ) otherlv_3= 'end' ) ;
    public final EObject ruleScheduleRegExp() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_exp_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1886:28: ( (otherlv_0= 'schedule' otherlv_1= 'regexp' ( (lv_exp_2_0= ruleRegExp ) ) otherlv_3= 'end' ) )
            // InternalCal.g:1887:1: (otherlv_0= 'schedule' otherlv_1= 'regexp' ( (lv_exp_2_0= ruleRegExp ) ) otherlv_3= 'end' )
            {
            // InternalCal.g:1887:1: (otherlv_0= 'schedule' otherlv_1= 'regexp' ( (lv_exp_2_0= ruleRegExp ) ) otherlv_3= 'end' )
            // InternalCal.g:1887:3: otherlv_0= 'schedule' otherlv_1= 'regexp' ( (lv_exp_2_0= ruleRegExp ) ) otherlv_3= 'end'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_40); 

                	newLeafNode(otherlv_0, grammarAccess.getScheduleRegExpAccess().getScheduleKeyword_0());
                
            otherlv_1=(Token)match(input,39,FOLLOW_41); 

                	newLeafNode(otherlv_1, grammarAccess.getScheduleRegExpAccess().getRegexpKeyword_1());
                
            // InternalCal.g:1895:1: ( (lv_exp_2_0= ruleRegExp ) )
            // InternalCal.g:1896:1: (lv_exp_2_0= ruleRegExp )
            {
            // InternalCal.g:1896:1: (lv_exp_2_0= ruleRegExp )
            // InternalCal.g:1897:3: lv_exp_2_0= ruleRegExp
            {
             
            	        newCompositeNode(grammarAccess.getScheduleRegExpAccess().getExpRegExpParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_26);
            lv_exp_2_0=ruleRegExp();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getScheduleRegExpRule());
            	        }
                   		set(
                   			current, 
                   			"exp",
                    		lv_exp_2_0, 
                    		"net.sf.orcc.cal.Cal.RegExp");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_3, grammarAccess.getScheduleRegExpAccess().getEndKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScheduleRegExp"


    // $ANTLR start "entryRuleRegExp"
    // InternalCal.g:1925:1: entryRuleRegExp returns [EObject current=null] : iv_ruleRegExp= ruleRegExp EOF ;
    public final EObject entryRuleRegExp() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegExp = null;


        try {
            // InternalCal.g:1926:2: (iv_ruleRegExp= ruleRegExp EOF )
            // InternalCal.g:1927:2: iv_ruleRegExp= ruleRegExp EOF
            {
             newCompositeNode(grammarAccess.getRegExpRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegExp=ruleRegExp();

            state._fsp--;

             current =iv_ruleRegExp; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegExp"


    // $ANTLR start "ruleRegExp"
    // InternalCal.g:1934:1: ruleRegExp returns [EObject current=null] : (this_RegExpConcatenation_0= ruleRegExpConcatenation ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )* ) ;
    public final EObject ruleRegExp() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_0=null;
        EObject this_RegExpConcatenation_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:1937:28: ( (this_RegExpConcatenation_0= ruleRegExpConcatenation ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )* ) )
            // InternalCal.g:1938:1: (this_RegExpConcatenation_0= ruleRegExpConcatenation ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )* )
            {
            // InternalCal.g:1938:1: (this_RegExpConcatenation_0= ruleRegExpConcatenation ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )* )
            // InternalCal.g:1939:5: this_RegExpConcatenation_0= ruleRegExpConcatenation ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getRegExpAccess().getRegExpConcatenationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_42);
            this_RegExpConcatenation_0=ruleRegExpConcatenation();

            state._fsp--;

             
                    current = this_RegExpConcatenation_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:1947:1: ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==40) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalCal.g:1947:2: () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleRegExpConcatenation ) )
            	    {
            	    // InternalCal.g:1947:2: ()
            	    // InternalCal.g:1948:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getRegExpAccess().getRegExpBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:1953:2: ( (lv_operator_2_0= '|' ) )
            	    // InternalCal.g:1954:1: (lv_operator_2_0= '|' )
            	    {
            	    // InternalCal.g:1954:1: (lv_operator_2_0= '|' )
            	    // InternalCal.g:1955:3: lv_operator_2_0= '|'
            	    {
            	    lv_operator_2_0=(Token)match(input,40,FOLLOW_41); 

            	            newLeafNode(lv_operator_2_0, grammarAccess.getRegExpAccess().getOperatorVerticalLineKeyword_1_1_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getRegExpRule());
            	    	        }
            	           		setWithLastConsumed(current, "operator", lv_operator_2_0, "|");
            	    	    

            	    }


            	    }

            	    // InternalCal.g:1968:2: ( (lv_right_3_0= ruleRegExpConcatenation ) )
            	    // InternalCal.g:1969:1: (lv_right_3_0= ruleRegExpConcatenation )
            	    {
            	    // InternalCal.g:1969:1: (lv_right_3_0= ruleRegExpConcatenation )
            	    // InternalCal.g:1970:3: lv_right_3_0= ruleRegExpConcatenation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getRegExpAccess().getRightRegExpConcatenationParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_42);
            	    lv_right_3_0=ruleRegExpConcatenation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getRegExpRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.RegExpConcatenation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegExp"


    // $ANTLR start "entryRuleRegExpConcatenation"
    // InternalCal.g:1994:1: entryRuleRegExpConcatenation returns [EObject current=null] : iv_ruleRegExpConcatenation= ruleRegExpConcatenation EOF ;
    public final EObject entryRuleRegExpConcatenation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegExpConcatenation = null;


        try {
            // InternalCal.g:1995:2: (iv_ruleRegExpConcatenation= ruleRegExpConcatenation EOF )
            // InternalCal.g:1996:2: iv_ruleRegExpConcatenation= ruleRegExpConcatenation EOF
            {
             newCompositeNode(grammarAccess.getRegExpConcatenationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegExpConcatenation=ruleRegExpConcatenation();

            state._fsp--;

             current =iv_ruleRegExpConcatenation; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegExpConcatenation"


    // $ANTLR start "ruleRegExpConcatenation"
    // InternalCal.g:2003:1: ruleRegExpConcatenation returns [EObject current=null] : (this_RegExpPostfix_0= ruleRegExpPostfix ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )* ) ;
    public final EObject ruleRegExpConcatenation() throws RecognitionException {
        EObject current = null;

        EObject this_RegExpPostfix_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2006:28: ( (this_RegExpPostfix_0= ruleRegExpPostfix ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )* ) )
            // InternalCal.g:2007:1: (this_RegExpPostfix_0= ruleRegExpPostfix ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )* )
            {
            // InternalCal.g:2007:1: (this_RegExpPostfix_0= ruleRegExpPostfix ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )* )
            // InternalCal.g:2008:5: this_RegExpPostfix_0= ruleRegExpPostfix ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getRegExpConcatenationAccess().getRegExpPostfixParserRuleCall_0()); 
                
            pushFollow(FOLLOW_43);
            this_RegExpPostfix_0=ruleRegExpPostfix();

            state._fsp--;

             
                    current = this_RegExpPostfix_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:2016:1: ( () ( (lv_right_2_0= ruleRegExpPostfix ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ID||LA38_0==25) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalCal.g:2016:2: () ( (lv_right_2_0= ruleRegExpPostfix ) )
            	    {
            	    // InternalCal.g:2016:2: ()
            	    // InternalCal.g:2017:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getRegExpConcatenationAccess().getRegExpBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:2022:2: ( (lv_right_2_0= ruleRegExpPostfix ) )
            	    // InternalCal.g:2023:1: (lv_right_2_0= ruleRegExpPostfix )
            	    {
            	    // InternalCal.g:2023:1: (lv_right_2_0= ruleRegExpPostfix )
            	    // InternalCal.g:2024:3: lv_right_2_0= ruleRegExpPostfix
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getRegExpConcatenationAccess().getRightRegExpPostfixParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_43);
            	    lv_right_2_0=ruleRegExpPostfix();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getRegExpConcatenationRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_2_0, 
            	            		"net.sf.orcc.cal.Cal.RegExpPostfix");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegExpConcatenation"


    // $ANTLR start "entryRuleRegExpPostfix"
    // InternalCal.g:2048:1: entryRuleRegExpPostfix returns [EObject current=null] : iv_ruleRegExpPostfix= ruleRegExpPostfix EOF ;
    public final EObject entryRuleRegExpPostfix() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegExpPostfix = null;


        try {
            // InternalCal.g:2049:2: (iv_ruleRegExpPostfix= ruleRegExpPostfix EOF )
            // InternalCal.g:2050:2: iv_ruleRegExpPostfix= ruleRegExpPostfix EOF
            {
             newCompositeNode(grammarAccess.getRegExpPostfixRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegExpPostfix=ruleRegExpPostfix();

            state._fsp--;

             current =iv_ruleRegExpPostfix; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegExpPostfix"


    // $ANTLR start "ruleRegExpPostfix"
    // InternalCal.g:2057:1: ruleRegExpPostfix returns [EObject current=null] : (this_RegExpGrouping_0= ruleRegExpGrouping ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )* ) ;
    public final EObject ruleRegExpPostfix() throws RecognitionException {
        EObject current = null;

        Token lv_unaryOperator_2_1=null;
        Token lv_unaryOperator_2_2=null;
        EObject this_RegExpGrouping_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2060:28: ( (this_RegExpGrouping_0= ruleRegExpGrouping ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )* ) )
            // InternalCal.g:2061:1: (this_RegExpGrouping_0= ruleRegExpGrouping ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )* )
            {
            // InternalCal.g:2061:1: (this_RegExpGrouping_0= ruleRegExpGrouping ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )* )
            // InternalCal.g:2062:5: this_RegExpGrouping_0= ruleRegExpGrouping ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getRegExpPostfixAccess().getRegExpGroupingParserRuleCall_0()); 
                
            pushFollow(FOLLOW_44);
            this_RegExpGrouping_0=ruleRegExpGrouping();

            state._fsp--;

             
                    current = this_RegExpGrouping_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:2070:1: ( () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=41 && LA40_0<=42)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalCal.g:2070:2: () ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) )
            	    {
            	    // InternalCal.g:2070:2: ()
            	    // InternalCal.g:2071:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getRegExpPostfixAccess().getRegExpUnaryChildAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:2076:2: ( ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) ) )
            	    // InternalCal.g:2077:1: ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) )
            	    {
            	    // InternalCal.g:2077:1: ( (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' ) )
            	    // InternalCal.g:2078:1: (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' )
            	    {
            	    // InternalCal.g:2078:1: (lv_unaryOperator_2_1= '*' | lv_unaryOperator_2_2= '?' )
            	    int alt39=2;
            	    int LA39_0 = input.LA(1);

            	    if ( (LA39_0==41) ) {
            	        alt39=1;
            	    }
            	    else if ( (LA39_0==42) ) {
            	        alt39=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 39, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt39) {
            	        case 1 :
            	            // InternalCal.g:2079:3: lv_unaryOperator_2_1= '*'
            	            {
            	            lv_unaryOperator_2_1=(Token)match(input,41,FOLLOW_44); 

            	                    newLeafNode(lv_unaryOperator_2_1, grammarAccess.getRegExpPostfixAccess().getUnaryOperatorAsteriskKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getRegExpPostfixRule());
            	            	        }
            	                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:2091:8: lv_unaryOperator_2_2= '?'
            	            {
            	            lv_unaryOperator_2_2=(Token)match(input,42,FOLLOW_44); 

            	                    newLeafNode(lv_unaryOperator_2_2, grammarAccess.getRegExpPostfixAccess().getUnaryOperatorQuestionMarkKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getRegExpPostfixRule());
            	            	        }
            	                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegExpPostfix"


    // $ANTLR start "entryRuleRegExpGrouping"
    // InternalCal.g:2114:1: entryRuleRegExpGrouping returns [EObject current=null] : iv_ruleRegExpGrouping= ruleRegExpGrouping EOF ;
    public final EObject entryRuleRegExpGrouping() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegExpGrouping = null;


        try {
            // InternalCal.g:2115:2: (iv_ruleRegExpGrouping= ruleRegExpGrouping EOF )
            // InternalCal.g:2116:2: iv_ruleRegExpGrouping= ruleRegExpGrouping EOF
            {
             newCompositeNode(grammarAccess.getRegExpGroupingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegExpGrouping=ruleRegExpGrouping();

            state._fsp--;

             current =iv_ruleRegExpGrouping; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegExpGrouping"


    // $ANTLR start "ruleRegExpGrouping"
    // InternalCal.g:2123:1: ruleRegExpGrouping returns [EObject current=null] : (this_RegExpTerminal_0= ruleRegExpTerminal | (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' ) ) ;
    public final EObject ruleRegExpGrouping() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject this_RegExpTerminal_0 = null;

        EObject this_RegExp_2 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2126:28: ( (this_RegExpTerminal_0= ruleRegExpTerminal | (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' ) ) )
            // InternalCal.g:2127:1: (this_RegExpTerminal_0= ruleRegExpTerminal | (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' ) )
            {
            // InternalCal.g:2127:1: (this_RegExpTerminal_0= ruleRegExpTerminal | (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' ) )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_ID) ) {
                alt41=1;
            }
            else if ( (LA41_0==25) ) {
                alt41=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // InternalCal.g:2128:5: this_RegExpTerminal_0= ruleRegExpTerminal
                    {
                     
                            newCompositeNode(grammarAccess.getRegExpGroupingAccess().getRegExpTerminalParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_2);
                    this_RegExpTerminal_0=ruleRegExpTerminal();

                    state._fsp--;

                     
                            current = this_RegExpTerminal_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:2137:6: (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' )
                    {
                    // InternalCal.g:2137:6: (otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')' )
                    // InternalCal.g:2137:8: otherlv_1= '(' this_RegExp_2= ruleRegExp otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,25,FOLLOW_41); 

                        	newLeafNode(otherlv_1, grammarAccess.getRegExpGroupingAccess().getLeftParenthesisKeyword_1_0());
                        
                     
                            newCompositeNode(grammarAccess.getRegExpGroupingAccess().getRegExpParserRuleCall_1_1()); 
                        
                    pushFollow(FOLLOW_37);
                    this_RegExp_2=ruleRegExp();

                    state._fsp--;

                     
                            current = this_RegExp_2; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_3=(Token)match(input,27,FOLLOW_2); 

                        	newLeafNode(otherlv_3, grammarAccess.getRegExpGroupingAccess().getRightParenthesisKeyword_1_2());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegExpGrouping"


    // $ANTLR start "entryRuleRegExpTerminal"
    // InternalCal.g:2162:1: entryRuleRegExpTerminal returns [EObject current=null] : iv_ruleRegExpTerminal= ruleRegExpTerminal EOF ;
    public final EObject entryRuleRegExpTerminal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegExpTerminal = null;


        try {
            // InternalCal.g:2163:2: (iv_ruleRegExpTerminal= ruleRegExpTerminal EOF )
            // InternalCal.g:2164:2: iv_ruleRegExpTerminal= ruleRegExpTerminal EOF
            {
             newCompositeNode(grammarAccess.getRegExpTerminalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegExpTerminal=ruleRegExpTerminal();

            state._fsp--;

             current =iv_ruleRegExpTerminal; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegExpTerminal"


    // $ANTLR start "ruleRegExpTerminal"
    // InternalCal.g:2171:1: ruleRegExpTerminal returns [EObject current=null] : ( () ( (lv_tag_1_0= ruleAstTag ) ) ) ;
    public final EObject ruleRegExpTerminal() throws RecognitionException {
        EObject current = null;

        EObject lv_tag_1_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2174:28: ( ( () ( (lv_tag_1_0= ruleAstTag ) ) ) )
            // InternalCal.g:2175:1: ( () ( (lv_tag_1_0= ruleAstTag ) ) )
            {
            // InternalCal.g:2175:1: ( () ( (lv_tag_1_0= ruleAstTag ) ) )
            // InternalCal.g:2175:2: () ( (lv_tag_1_0= ruleAstTag ) )
            {
            // InternalCal.g:2175:2: ()
            // InternalCal.g:2176:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getRegExpTerminalAccess().getRegExpTagAction_0(),
                        current);
                

            }

            // InternalCal.g:2181:2: ( (lv_tag_1_0= ruleAstTag ) )
            // InternalCal.g:2182:1: (lv_tag_1_0= ruleAstTag )
            {
            // InternalCal.g:2182:1: (lv_tag_1_0= ruleAstTag )
            // InternalCal.g:2183:3: lv_tag_1_0= ruleAstTag
            {
             
            	        newCompositeNode(grammarAccess.getRegExpTerminalAccess().getTagAstTagParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_2);
            lv_tag_1_0=ruleAstTag();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRegExpTerminalRule());
            	        }
                   		set(
                   			current, 
                   			"tag",
                    		lv_tag_1_0, 
                    		"net.sf.orcc.cal.Cal.AstTag");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegExpTerminal"


    // $ANTLR start "entryRuleLocalFsm"
    // InternalCal.g:2207:1: entryRuleLocalFsm returns [EObject current=null] : iv_ruleLocalFsm= ruleLocalFsm EOF ;
    public final EObject entryRuleLocalFsm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalFsm = null;


        try {
            // InternalCal.g:2208:2: (iv_ruleLocalFsm= ruleLocalFsm EOF )
            // InternalCal.g:2209:2: iv_ruleLocalFsm= ruleLocalFsm EOF
            {
             newCompositeNode(grammarAccess.getLocalFsmRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLocalFsm=ruleLocalFsm();

            state._fsp--;

             current =iv_ruleLocalFsm; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalFsm"


    // $ANTLR start "ruleLocalFsm"
    // InternalCal.g:2216:1: ruleLocalFsm returns [EObject current=null] : (otherlv_0= 'local' otherlv_1= 'fsm' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' ) ;
    public final EObject ruleLocalFsm() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_contents_4_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2219:28: ( (otherlv_0= 'local' otherlv_1= 'fsm' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' ) )
            // InternalCal.g:2220:1: (otherlv_0= 'local' otherlv_1= 'fsm' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' )
            {
            // InternalCal.g:2220:1: (otherlv_0= 'local' otherlv_1= 'fsm' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end' )
            // InternalCal.g:2220:3: otherlv_0= 'local' otherlv_1= 'fsm' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_contents_4_0= ruleFsm ) ) otherlv_5= 'end'
            {
            otherlv_0=(Token)match(input,43,FOLLOW_35); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalFsmAccess().getLocalKeyword_0());
                
            otherlv_1=(Token)match(input,37,FOLLOW_3); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalFsmAccess().getFsmKeyword_1());
                
            // InternalCal.g:2228:1: ( (lv_name_2_0= RULE_ID ) )
            // InternalCal.g:2229:1: (lv_name_2_0= RULE_ID )
            {
            // InternalCal.g:2229:1: (lv_name_2_0= RULE_ID )
            // InternalCal.g:2230:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            			newLeafNode(lv_name_2_0, grammarAccess.getLocalFsmAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalFsmRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,22,FOLLOW_34); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalFsmAccess().getColonKeyword_3());
                
            // InternalCal.g:2250:1: ( (lv_contents_4_0= ruleFsm ) )
            // InternalCal.g:2251:1: (lv_contents_4_0= ruleFsm )
            {
            // InternalCal.g:2251:1: (lv_contents_4_0= ruleFsm )
            // InternalCal.g:2252:3: lv_contents_4_0= ruleFsm
            {
             
            	        newCompositeNode(grammarAccess.getLocalFsmAccess().getContentsFsmParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_26);
            lv_contents_4_0=ruleFsm();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalFsmRule());
            	        }
                   		set(
                   			current, 
                   			"contents",
                    		lv_contents_4_0, 
                    		"net.sf.orcc.cal.Cal.Fsm");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_5=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_5, grammarAccess.getLocalFsmAccess().getEndKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalFsm"


    // $ANTLR start "entryRuleAstAction"
    // InternalCal.g:2280:1: entryRuleAstAction returns [EObject current=null] : iv_ruleAstAction= ruleAstAction EOF ;
    public final EObject entryRuleAstAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstAction = null;


        try {
            // InternalCal.g:2281:2: (iv_ruleAstAction= ruleAstAction EOF )
            // InternalCal.g:2282:2: iv_ruleAstAction= ruleAstAction EOF
            {
             newCompositeNode(grammarAccess.getAstActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstAction=ruleAstAction();

            state._fsp--;

             current =iv_ruleAstAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstAction"


    // $ANTLR start "ruleAstAction"
    // InternalCal.g:2289:1: ruleAstAction returns [EObject current=null] : ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'action' ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )? otherlv_8= '==>' ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )? (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )? (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )? otherlv_20= 'end' ) ;
    public final EObject ruleAstAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        EObject lv_annotations_1_0 = null;

        EObject lv_tag_2_0 = null;

        EObject lv_inputs_5_0 = null;

        EObject lv_inputs_7_0 = null;

        EObject lv_outputs_9_0 = null;

        EObject lv_outputs_11_0 = null;

        EObject lv_guard_13_0 = null;

        EObject lv_variables_15_0 = null;

        EObject lv_variables_17_0 = null;

        EObject lv_statements_19_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2292:28: ( ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'action' ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )? otherlv_8= '==>' ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )? (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )? (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )? otherlv_20= 'end' ) )
            // InternalCal.g:2293:1: ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'action' ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )? otherlv_8= '==>' ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )? (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )? (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )? otherlv_20= 'end' )
            {
            // InternalCal.g:2293:1: ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'action' ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )? otherlv_8= '==>' ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )? (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )? (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )? otherlv_20= 'end' )
            // InternalCal.g:2293:2: () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'action' ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )? otherlv_8= '==>' ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )? (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )? (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )? otherlv_20= 'end'
            {
            // InternalCal.g:2293:2: ()
            // InternalCal.g:2294:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstActionAccess().getAstActionAction_0(),
                        current);
                

            }

            // InternalCal.g:2299:2: ( (lv_annotations_1_0= ruleAstAnnotation ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==94) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalCal.g:2300:1: (lv_annotations_1_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:2300:1: (lv_annotations_1_0= ruleAstAnnotation )
            	    // InternalCal.g:2301:3: lv_annotations_1_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstActionAccess().getAnnotationsAstAnnotationParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_45);
            	    lv_annotations_1_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_1_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            // InternalCal.g:2317:3: ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RULE_ID) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalCal.g:2317:4: ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':'
                    {
                    // InternalCal.g:2317:4: ( (lv_tag_2_0= ruleAstTag ) )
                    // InternalCal.g:2318:1: (lv_tag_2_0= ruleAstTag )
                    {
                    // InternalCal.g:2318:1: (lv_tag_2_0= ruleAstTag )
                    // InternalCal.g:2319:3: lv_tag_2_0= ruleAstTag
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActionAccess().getTagAstTagParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_8);
                    lv_tag_2_0=ruleAstTag();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	        }
                           		set(
                           			current, 
                           			"tag",
                            		lv_tag_2_0, 
                            		"net.sf.orcc.cal.Cal.AstTag");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,22,FOLLOW_46); 

                        	newLeafNode(otherlv_3, grammarAccess.getAstActionAccess().getColonKeyword_2_1());
                        

                    }
                    break;

            }

            otherlv_4=(Token)match(input,44,FOLLOW_47); 

                	newLeafNode(otherlv_4, grammarAccess.getAstActionAccess().getActionKeyword_3());
                
            // InternalCal.g:2343:1: ( ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )* )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==RULE_ID) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalCal.g:2343:2: ( (lv_inputs_5_0= ruleInputPattern ) ) (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )*
                    {
                    // InternalCal.g:2343:2: ( (lv_inputs_5_0= ruleInputPattern ) )
                    // InternalCal.g:2344:1: (lv_inputs_5_0= ruleInputPattern )
                    {
                    // InternalCal.g:2344:1: (lv_inputs_5_0= ruleInputPattern )
                    // InternalCal.g:2345:3: lv_inputs_5_0= ruleInputPattern
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActionAccess().getInputsInputPatternParserRuleCall_4_0_0()); 
                    	    
                    pushFollow(FOLLOW_18);
                    lv_inputs_5_0=ruleInputPattern();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	        }
                           		add(
                           			current, 
                           			"inputs",
                            		lv_inputs_5_0, 
                            		"net.sf.orcc.cal.Cal.InputPattern");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:2361:2: (otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) ) )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==26) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // InternalCal.g:2361:4: otherlv_6= ',' ( (lv_inputs_7_0= ruleInputPattern ) )
                    	    {
                    	    otherlv_6=(Token)match(input,26,FOLLOW_3); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getAstActionAccess().getCommaKeyword_4_1_0());
                    	        
                    	    // InternalCal.g:2365:1: ( (lv_inputs_7_0= ruleInputPattern ) )
                    	    // InternalCal.g:2366:1: (lv_inputs_7_0= ruleInputPattern )
                    	    {
                    	    // InternalCal.g:2366:1: (lv_inputs_7_0= ruleInputPattern )
                    	    // InternalCal.g:2367:3: lv_inputs_7_0= ruleInputPattern
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActionAccess().getInputsInputPatternParserRuleCall_4_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_18);
                    	    lv_inputs_7_0=ruleInputPattern();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"inputs",
                    	            		lv_inputs_7_0, 
                    	            		"net.sf.orcc.cal.Cal.InputPattern");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_8=(Token)match(input,28,FOLLOW_48); 

                	newLeafNode(otherlv_8, grammarAccess.getAstActionAccess().getEqualsSignEqualsSignGreaterThanSignKeyword_5());
                
            // InternalCal.g:2387:1: ( ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )* )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==RULE_ID) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalCal.g:2387:2: ( (lv_outputs_9_0= ruleOutputPattern ) ) (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )*
                    {
                    // InternalCal.g:2387:2: ( (lv_outputs_9_0= ruleOutputPattern ) )
                    // InternalCal.g:2388:1: (lv_outputs_9_0= ruleOutputPattern )
                    {
                    // InternalCal.g:2388:1: (lv_outputs_9_0= ruleOutputPattern )
                    // InternalCal.g:2389:3: lv_outputs_9_0= ruleOutputPattern
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActionAccess().getOutputsOutputPatternParserRuleCall_6_0_0()); 
                    	    
                    pushFollow(FOLLOW_49);
                    lv_outputs_9_0=ruleOutputPattern();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	        }
                           		add(
                           			current, 
                           			"outputs",
                            		lv_outputs_9_0, 
                            		"net.sf.orcc.cal.Cal.OutputPattern");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:2405:2: (otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) ) )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==26) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // InternalCal.g:2405:4: otherlv_10= ',' ( (lv_outputs_11_0= ruleOutputPattern ) )
                    	    {
                    	    otherlv_10=(Token)match(input,26,FOLLOW_3); 

                    	        	newLeafNode(otherlv_10, grammarAccess.getAstActionAccess().getCommaKeyword_6_1_0());
                    	        
                    	    // InternalCal.g:2409:1: ( (lv_outputs_11_0= ruleOutputPattern ) )
                    	    // InternalCal.g:2410:1: (lv_outputs_11_0= ruleOutputPattern )
                    	    {
                    	    // InternalCal.g:2410:1: (lv_outputs_11_0= ruleOutputPattern )
                    	    // InternalCal.g:2411:3: lv_outputs_11_0= ruleOutputPattern
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActionAccess().getOutputsOutputPatternParserRuleCall_6_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_49);
                    	    lv_outputs_11_0=ruleOutputPattern();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"outputs",
                    	            		lv_outputs_11_0, 
                    	            		"net.sf.orcc.cal.Cal.OutputPattern");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalCal.g:2427:6: (otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==45) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalCal.g:2427:8: otherlv_12= 'guard' ( (lv_guard_13_0= ruleGuard ) )
                    {
                    otherlv_12=(Token)match(input,45,FOLLOW_13); 

                        	newLeafNode(otherlv_12, grammarAccess.getAstActionAccess().getGuardKeyword_7_0());
                        
                    // InternalCal.g:2431:1: ( (lv_guard_13_0= ruleGuard ) )
                    // InternalCal.g:2432:1: (lv_guard_13_0= ruleGuard )
                    {
                    // InternalCal.g:2432:1: (lv_guard_13_0= ruleGuard )
                    // InternalCal.g:2433:3: lv_guard_13_0= ruleGuard
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActionAccess().getGuardGuardParserRuleCall_7_1_0()); 
                    	    
                    pushFollow(FOLLOW_50);
                    lv_guard_13_0=ruleGuard();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	        }
                           		set(
                           			current, 
                           			"guard",
                            		lv_guard_13_0, 
                            		"net.sf.orcc.cal.Cal.Guard");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // InternalCal.g:2449:4: (otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )* )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==31) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalCal.g:2449:6: otherlv_14= 'var' ( (lv_variables_15_0= ruleValuedVariableDeclaration ) ) (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )*
                    {
                    otherlv_14=(Token)match(input,31,FOLLOW_16); 

                        	newLeafNode(otherlv_14, grammarAccess.getAstActionAccess().getVarKeyword_8_0());
                        
                    // InternalCal.g:2453:1: ( (lv_variables_15_0= ruleValuedVariableDeclaration ) )
                    // InternalCal.g:2454:1: (lv_variables_15_0= ruleValuedVariableDeclaration )
                    {
                    // InternalCal.g:2454:1: (lv_variables_15_0= ruleValuedVariableDeclaration )
                    // InternalCal.g:2455:3: lv_variables_15_0= ruleValuedVariableDeclaration
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstActionAccess().getVariablesValuedVariableDeclarationParserRuleCall_8_1_0()); 
                    	    
                    pushFollow(FOLLOW_51);
                    lv_variables_15_0=ruleValuedVariableDeclaration();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	        }
                           		add(
                           			current, 
                           			"variables",
                            		lv_variables_15_0, 
                            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:2471:2: (otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) ) )*
                    loop49:
                    do {
                        int alt49=2;
                        int LA49_0 = input.LA(1);

                        if ( (LA49_0==26) ) {
                            alt49=1;
                        }


                        switch (alt49) {
                    	case 1 :
                    	    // InternalCal.g:2471:4: otherlv_16= ',' ( (lv_variables_17_0= ruleValuedVariableDeclaration ) )
                    	    {
                    	    otherlv_16=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_16, grammarAccess.getAstActionAccess().getCommaKeyword_8_2_0());
                    	        
                    	    // InternalCal.g:2475:1: ( (lv_variables_17_0= ruleValuedVariableDeclaration ) )
                    	    // InternalCal.g:2476:1: (lv_variables_17_0= ruleValuedVariableDeclaration )
                    	    {
                    	    // InternalCal.g:2476:1: (lv_variables_17_0= ruleValuedVariableDeclaration )
                    	    // InternalCal.g:2477:3: lv_variables_17_0= ruleValuedVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActionAccess().getVariablesValuedVariableDeclarationParserRuleCall_8_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_51);
                    	    lv_variables_17_0=ruleValuedVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"variables",
                    	            		lv_variables_17_0, 
                    	            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop49;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalCal.g:2493:6: (otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )* )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==46) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalCal.g:2493:8: otherlv_18= 'do' ( (lv_statements_19_0= ruleStatement ) )*
                    {
                    otherlv_18=(Token)match(input,46,FOLLOW_30); 

                        	newLeafNode(otherlv_18, grammarAccess.getAstActionAccess().getDoKeyword_9_0());
                        
                    // InternalCal.g:2497:1: ( (lv_statements_19_0= ruleStatement ) )*
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==RULE_ID||LA51_0==52||LA51_0==55||LA51_0==59||LA51_0==94) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // InternalCal.g:2498:1: (lv_statements_19_0= ruleStatement )
                    	    {
                    	    // InternalCal.g:2498:1: (lv_statements_19_0= ruleStatement )
                    	    // InternalCal.g:2499:3: lv_statements_19_0= ruleStatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstActionAccess().getStatementsStatementParserRuleCall_9_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_30);
                    	    lv_statements_19_0=ruleStatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstActionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"statements",
                    	            		lv_statements_19_0, 
                    	            		"net.sf.orcc.cal.Cal.Statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_20=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_20, grammarAccess.getAstActionAccess().getEndKeyword_10());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstAction"


    // $ANTLR start "entryRuleInitialize"
    // InternalCal.g:2527:1: entryRuleInitialize returns [EObject current=null] : iv_ruleInitialize= ruleInitialize EOF ;
    public final EObject entryRuleInitialize() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitialize = null;


        try {
            // InternalCal.g:2528:2: (iv_ruleInitialize= ruleInitialize EOF )
            // InternalCal.g:2529:2: iv_ruleInitialize= ruleInitialize EOF
            {
             newCompositeNode(grammarAccess.getInitializeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInitialize=ruleInitialize();

            state._fsp--;

             current =iv_ruleInitialize; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInitialize"


    // $ANTLR start "ruleInitialize"
    // InternalCal.g:2536:1: ruleInitialize returns [EObject current=null] : ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'initialize' otherlv_5= '==>' ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )? (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )? (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )? otherlv_17= 'end' ) ;
    public final EObject ruleInitialize() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        EObject lv_annotations_1_0 = null;

        EObject lv_tag_2_0 = null;

        EObject lv_outputs_6_0 = null;

        EObject lv_outputs_8_0 = null;

        EObject lv_guard_10_0 = null;

        EObject lv_variables_12_0 = null;

        EObject lv_variables_14_0 = null;

        EObject lv_statements_16_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2539:28: ( ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'initialize' otherlv_5= '==>' ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )? (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )? (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )? otherlv_17= 'end' ) )
            // InternalCal.g:2540:1: ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'initialize' otherlv_5= '==>' ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )? (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )? (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )? otherlv_17= 'end' )
            {
            // InternalCal.g:2540:1: ( () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'initialize' otherlv_5= '==>' ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )? (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )? (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )? otherlv_17= 'end' )
            // InternalCal.g:2540:2: () ( (lv_annotations_1_0= ruleAstAnnotation ) )* ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )? otherlv_4= 'initialize' otherlv_5= '==>' ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )? (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )? (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )? (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )? otherlv_17= 'end'
            {
            // InternalCal.g:2540:2: ()
            // InternalCal.g:2541:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getInitializeAccess().getAstActionAction_0(),
                        current);
                

            }

            // InternalCal.g:2546:2: ( (lv_annotations_1_0= ruleAstAnnotation ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==94) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalCal.g:2547:1: (lv_annotations_1_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:2547:1: (lv_annotations_1_0= ruleAstAnnotation )
            	    // InternalCal.g:2548:3: lv_annotations_1_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getInitializeAccess().getAnnotationsAstAnnotationParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_52);
            	    lv_annotations_1_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_1_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            // InternalCal.g:2564:3: ( ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':' )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_ID) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalCal.g:2564:4: ( (lv_tag_2_0= ruleAstTag ) ) otherlv_3= ':'
                    {
                    // InternalCal.g:2564:4: ( (lv_tag_2_0= ruleAstTag ) )
                    // InternalCal.g:2565:1: (lv_tag_2_0= ruleAstTag )
                    {
                    // InternalCal.g:2565:1: (lv_tag_2_0= ruleAstTag )
                    // InternalCal.g:2566:3: lv_tag_2_0= ruleAstTag
                    {
                     
                    	        newCompositeNode(grammarAccess.getInitializeAccess().getTagAstTagParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_8);
                    lv_tag_2_0=ruleAstTag();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	        }
                           		set(
                           			current, 
                           			"tag",
                            		lv_tag_2_0, 
                            		"net.sf.orcc.cal.Cal.AstTag");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,22,FOLLOW_53); 

                        	newLeafNode(otherlv_3, grammarAccess.getInitializeAccess().getColonKeyword_2_1());
                        

                    }
                    break;

            }

            otherlv_4=(Token)match(input,47,FOLLOW_54); 

                	newLeafNode(otherlv_4, grammarAccess.getInitializeAccess().getInitializeKeyword_3());
                
            otherlv_5=(Token)match(input,28,FOLLOW_48); 

                	newLeafNode(otherlv_5, grammarAccess.getInitializeAccess().getEqualsSignEqualsSignGreaterThanSignKeyword_4());
                
            // InternalCal.g:2594:1: ( ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )* )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_ID) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalCal.g:2594:2: ( (lv_outputs_6_0= ruleOutputPattern ) ) (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )*
                    {
                    // InternalCal.g:2594:2: ( (lv_outputs_6_0= ruleOutputPattern ) )
                    // InternalCal.g:2595:1: (lv_outputs_6_0= ruleOutputPattern )
                    {
                    // InternalCal.g:2595:1: (lv_outputs_6_0= ruleOutputPattern )
                    // InternalCal.g:2596:3: lv_outputs_6_0= ruleOutputPattern
                    {
                     
                    	        newCompositeNode(grammarAccess.getInitializeAccess().getOutputsOutputPatternParserRuleCall_5_0_0()); 
                    	    
                    pushFollow(FOLLOW_49);
                    lv_outputs_6_0=ruleOutputPattern();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	        }
                           		add(
                           			current, 
                           			"outputs",
                            		lv_outputs_6_0, 
                            		"net.sf.orcc.cal.Cal.OutputPattern");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:2612:2: (otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) ) )*
                    loop55:
                    do {
                        int alt55=2;
                        int LA55_0 = input.LA(1);

                        if ( (LA55_0==26) ) {
                            alt55=1;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // InternalCal.g:2612:4: otherlv_7= ',' ( (lv_outputs_8_0= ruleOutputPattern ) )
                    	    {
                    	    otherlv_7=(Token)match(input,26,FOLLOW_3); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getInitializeAccess().getCommaKeyword_5_1_0());
                    	        
                    	    // InternalCal.g:2616:1: ( (lv_outputs_8_0= ruleOutputPattern ) )
                    	    // InternalCal.g:2617:1: (lv_outputs_8_0= ruleOutputPattern )
                    	    {
                    	    // InternalCal.g:2617:1: (lv_outputs_8_0= ruleOutputPattern )
                    	    // InternalCal.g:2618:3: lv_outputs_8_0= ruleOutputPattern
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getInitializeAccess().getOutputsOutputPatternParserRuleCall_5_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_49);
                    	    lv_outputs_8_0=ruleOutputPattern();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"outputs",
                    	            		lv_outputs_8_0, 
                    	            		"net.sf.orcc.cal.Cal.OutputPattern");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop55;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalCal.g:2634:6: (otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==45) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalCal.g:2634:8: otherlv_9= 'guard' ( (lv_guard_10_0= ruleGuard ) )
                    {
                    otherlv_9=(Token)match(input,45,FOLLOW_13); 

                        	newLeafNode(otherlv_9, grammarAccess.getInitializeAccess().getGuardKeyword_6_0());
                        
                    // InternalCal.g:2638:1: ( (lv_guard_10_0= ruleGuard ) )
                    // InternalCal.g:2639:1: (lv_guard_10_0= ruleGuard )
                    {
                    // InternalCal.g:2639:1: (lv_guard_10_0= ruleGuard )
                    // InternalCal.g:2640:3: lv_guard_10_0= ruleGuard
                    {
                     
                    	        newCompositeNode(grammarAccess.getInitializeAccess().getGuardGuardParserRuleCall_6_1_0()); 
                    	    
                    pushFollow(FOLLOW_50);
                    lv_guard_10_0=ruleGuard();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	        }
                           		set(
                           			current, 
                           			"guard",
                            		lv_guard_10_0, 
                            		"net.sf.orcc.cal.Cal.Guard");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // InternalCal.g:2656:4: (otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )* )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==31) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalCal.g:2656:6: otherlv_11= 'var' ( (lv_variables_12_0= ruleValuedVariableDeclaration ) ) (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )*
                    {
                    otherlv_11=(Token)match(input,31,FOLLOW_16); 

                        	newLeafNode(otherlv_11, grammarAccess.getInitializeAccess().getVarKeyword_7_0());
                        
                    // InternalCal.g:2660:1: ( (lv_variables_12_0= ruleValuedVariableDeclaration ) )
                    // InternalCal.g:2661:1: (lv_variables_12_0= ruleValuedVariableDeclaration )
                    {
                    // InternalCal.g:2661:1: (lv_variables_12_0= ruleValuedVariableDeclaration )
                    // InternalCal.g:2662:3: lv_variables_12_0= ruleValuedVariableDeclaration
                    {
                     
                    	        newCompositeNode(grammarAccess.getInitializeAccess().getVariablesValuedVariableDeclarationParserRuleCall_7_1_0()); 
                    	    
                    pushFollow(FOLLOW_51);
                    lv_variables_12_0=ruleValuedVariableDeclaration();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	        }
                           		add(
                           			current, 
                           			"variables",
                            		lv_variables_12_0, 
                            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:2678:2: (otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) ) )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==26) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // InternalCal.g:2678:4: otherlv_13= ',' ( (lv_variables_14_0= ruleValuedVariableDeclaration ) )
                    	    {
                    	    otherlv_13=(Token)match(input,26,FOLLOW_16); 

                    	        	newLeafNode(otherlv_13, grammarAccess.getInitializeAccess().getCommaKeyword_7_2_0());
                    	        
                    	    // InternalCal.g:2682:1: ( (lv_variables_14_0= ruleValuedVariableDeclaration ) )
                    	    // InternalCal.g:2683:1: (lv_variables_14_0= ruleValuedVariableDeclaration )
                    	    {
                    	    // InternalCal.g:2683:1: (lv_variables_14_0= ruleValuedVariableDeclaration )
                    	    // InternalCal.g:2684:3: lv_variables_14_0= ruleValuedVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getInitializeAccess().getVariablesValuedVariableDeclarationParserRuleCall_7_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_51);
                    	    lv_variables_14_0=ruleValuedVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"variables",
                    	            		lv_variables_14_0, 
                    	            		"net.sf.orcc.cal.Cal.ValuedVariableDeclaration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalCal.g:2700:6: (otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )* )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==46) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalCal.g:2700:8: otherlv_15= 'do' ( (lv_statements_16_0= ruleStatement ) )*
                    {
                    otherlv_15=(Token)match(input,46,FOLLOW_30); 

                        	newLeafNode(otherlv_15, grammarAccess.getInitializeAccess().getDoKeyword_8_0());
                        
                    // InternalCal.g:2704:1: ( (lv_statements_16_0= ruleStatement ) )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==RULE_ID||LA60_0==52||LA60_0==55||LA60_0==59||LA60_0==94) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // InternalCal.g:2705:1: (lv_statements_16_0= ruleStatement )
                    	    {
                    	    // InternalCal.g:2705:1: (lv_statements_16_0= ruleStatement )
                    	    // InternalCal.g:2706:3: lv_statements_16_0= ruleStatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getInitializeAccess().getStatementsStatementParserRuleCall_8_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_30);
                    	    lv_statements_16_0=ruleStatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getInitializeRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"statements",
                    	            		lv_statements_16_0, 
                    	            		"net.sf.orcc.cal.Cal.Statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_17=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_17, grammarAccess.getInitializeAccess().getEndKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInitialize"


    // $ANTLR start "entryRuleToken"
    // InternalCal.g:2734:1: entryRuleToken returns [EObject current=null] : iv_ruleToken= ruleToken EOF ;
    public final EObject entryRuleToken() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleToken = null;


        try {
            // InternalCal.g:2735:2: (iv_ruleToken= ruleToken EOF )
            // InternalCal.g:2736:2: iv_ruleToken= ruleToken EOF
            {
             newCompositeNode(grammarAccess.getTokenRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleToken=ruleToken();

            state._fsp--;

             current =iv_ruleToken; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleToken"


    // $ANTLR start "ruleToken"
    // InternalCal.g:2743:1: ruleToken returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleToken() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;

         enterRule(); 
            
        try {
            // InternalCal.g:2746:28: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalCal.g:2747:1: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalCal.g:2747:1: ( (lv_name_0_0= RULE_ID ) )
            // InternalCal.g:2748:1: (lv_name_0_0= RULE_ID )
            {
            // InternalCal.g:2748:1: (lv_name_0_0= RULE_ID )
            // InternalCal.g:2749:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            			newLeafNode(lv_name_0_0, grammarAccess.getTokenAccess().getNameIDTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTokenRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleToken"


    // $ANTLR start "entryRuleInputPattern"
    // InternalCal.g:2773:1: entryRuleInputPattern returns [EObject current=null] : iv_ruleInputPattern= ruleInputPattern EOF ;
    public final EObject entryRuleInputPattern() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInputPattern = null;


        try {
            // InternalCal.g:2774:2: (iv_ruleInputPattern= ruleInputPattern EOF )
            // InternalCal.g:2775:2: iv_ruleInputPattern= ruleInputPattern EOF
            {
             newCompositeNode(grammarAccess.getInputPatternRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInputPattern=ruleInputPattern();

            state._fsp--;

             current =iv_ruleInputPattern; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInputPattern"


    // $ANTLR start "ruleInputPattern"
    // InternalCal.g:2782:1: ruleInputPattern returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_tokens_3_0= ruleToken ) ) (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? ) ;
    public final EObject ruleInputPattern() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_tokens_3_0 = null;

        EObject lv_tokens_5_0 = null;

        EObject lv_repeat_8_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2785:28: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_tokens_3_0= ruleToken ) ) (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? ) )
            // InternalCal.g:2786:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_tokens_3_0= ruleToken ) ) (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? )
            {
            // InternalCal.g:2786:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_tokens_3_0= ruleToken ) ) (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? )
            // InternalCal.g:2786:2: ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_tokens_3_0= ruleToken ) ) (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )?
            {
            // InternalCal.g:2786:2: ( (otherlv_0= RULE_ID ) )
            // InternalCal.g:2787:1: (otherlv_0= RULE_ID )
            {
            // InternalCal.g:2787:1: (otherlv_0= RULE_ID )
            // InternalCal.g:2788:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getInputPatternRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            		newLeafNode(otherlv_0, grammarAccess.getInputPatternAccess().getPortAstPortCrossReference_0_0()); 
            	

            }


            }

            otherlv_1=(Token)match(input,22,FOLLOW_55); 

                	newLeafNode(otherlv_1, grammarAccess.getInputPatternAccess().getColonKeyword_1());
                
            otherlv_2=(Token)match(input,48,FOLLOW_3); 

                	newLeafNode(otherlv_2, grammarAccess.getInputPatternAccess().getLeftSquareBracketKeyword_2());
                
            // InternalCal.g:2807:1: ( (lv_tokens_3_0= ruleToken ) )
            // InternalCal.g:2808:1: (lv_tokens_3_0= ruleToken )
            {
            // InternalCal.g:2808:1: (lv_tokens_3_0= ruleToken )
            // InternalCal.g:2809:3: lv_tokens_3_0= ruleToken
            {
             
            	        newCompositeNode(grammarAccess.getInputPatternAccess().getTokensTokenParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_56);
            lv_tokens_3_0=ruleToken();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getInputPatternRule());
            	        }
                   		add(
                   			current, 
                   			"tokens",
                    		lv_tokens_3_0, 
                    		"net.sf.orcc.cal.Cal.Token");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:2825:2: (otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==26) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalCal.g:2825:4: otherlv_4= ',' ( (lv_tokens_5_0= ruleToken ) )
            	    {
            	    otherlv_4=(Token)match(input,26,FOLLOW_3); 

            	        	newLeafNode(otherlv_4, grammarAccess.getInputPatternAccess().getCommaKeyword_4_0());
            	        
            	    // InternalCal.g:2829:1: ( (lv_tokens_5_0= ruleToken ) )
            	    // InternalCal.g:2830:1: (lv_tokens_5_0= ruleToken )
            	    {
            	    // InternalCal.g:2830:1: (lv_tokens_5_0= ruleToken )
            	    // InternalCal.g:2831:3: lv_tokens_5_0= ruleToken
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getInputPatternAccess().getTokensTokenParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_56);
            	    lv_tokens_5_0=ruleToken();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getInputPatternRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"tokens",
            	            		lv_tokens_5_0, 
            	            		"net.sf.orcc.cal.Cal.Token");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);

            otherlv_6=(Token)match(input,49,FOLLOW_57); 

                	newLeafNode(otherlv_6, grammarAccess.getInputPatternAccess().getRightSquareBracketKeyword_5());
                
            // InternalCal.g:2851:1: (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==50) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalCal.g:2851:3: otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) )
                    {
                    otherlv_7=(Token)match(input,50,FOLLOW_13); 

                        	newLeafNode(otherlv_7, grammarAccess.getInputPatternAccess().getRepeatKeyword_6_0());
                        
                    // InternalCal.g:2855:1: ( (lv_repeat_8_0= ruleAstExpression ) )
                    // InternalCal.g:2856:1: (lv_repeat_8_0= ruleAstExpression )
                    {
                    // InternalCal.g:2856:1: (lv_repeat_8_0= ruleAstExpression )
                    // InternalCal.g:2857:3: lv_repeat_8_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getInputPatternAccess().getRepeatAstExpressionParserRuleCall_6_1_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_repeat_8_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getInputPatternRule());
                    	        }
                           		set(
                           			current, 
                           			"repeat",
                            		lv_repeat_8_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInputPattern"


    // $ANTLR start "entryRuleOutputPattern"
    // InternalCal.g:2881:1: entryRuleOutputPattern returns [EObject current=null] : iv_ruleOutputPattern= ruleOutputPattern EOF ;
    public final EObject entryRuleOutputPattern() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOutputPattern = null;


        try {
            // InternalCal.g:2882:2: (iv_ruleOutputPattern= ruleOutputPattern EOF )
            // InternalCal.g:2883:2: iv_ruleOutputPattern= ruleOutputPattern EOF
            {
             newCompositeNode(grammarAccess.getOutputPatternRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOutputPattern=ruleOutputPattern();

            state._fsp--;

             current =iv_ruleOutputPattern; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOutputPattern"


    // $ANTLR start "ruleOutputPattern"
    // InternalCal.g:2890:1: ruleOutputPattern returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_values_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? ) ;
    public final EObject ruleOutputPattern() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_values_3_0 = null;

        EObject lv_values_5_0 = null;

        EObject lv_repeat_8_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:2893:28: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_values_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? ) )
            // InternalCal.g:2894:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_values_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? )
            {
            // InternalCal.g:2894:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_values_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )? )
            // InternalCal.g:2894:2: ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' otherlv_2= '[' ( (lv_values_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )* otherlv_6= ']' (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )?
            {
            // InternalCal.g:2894:2: ( (otherlv_0= RULE_ID ) )
            // InternalCal.g:2895:1: (otherlv_0= RULE_ID )
            {
            // InternalCal.g:2895:1: (otherlv_0= RULE_ID )
            // InternalCal.g:2896:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getOutputPatternRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            		newLeafNode(otherlv_0, grammarAccess.getOutputPatternAccess().getPortAstPortCrossReference_0_0()); 
            	

            }


            }

            otherlv_1=(Token)match(input,22,FOLLOW_55); 

                	newLeafNode(otherlv_1, grammarAccess.getOutputPatternAccess().getColonKeyword_1());
                
            otherlv_2=(Token)match(input,48,FOLLOW_13); 

                	newLeafNode(otherlv_2, grammarAccess.getOutputPatternAccess().getLeftSquareBracketKeyword_2());
                
            // InternalCal.g:2915:1: ( (lv_values_3_0= ruleAstExpression ) )
            // InternalCal.g:2916:1: (lv_values_3_0= ruleAstExpression )
            {
            // InternalCal.g:2916:1: (lv_values_3_0= ruleAstExpression )
            // InternalCal.g:2917:3: lv_values_3_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getOutputPatternAccess().getValuesAstExpressionParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_56);
            lv_values_3_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getOutputPatternRule());
            	        }
                   		add(
                   			current, 
                   			"values",
                    		lv_values_3_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:2933:2: (otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==26) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalCal.g:2933:4: otherlv_4= ',' ( (lv_values_5_0= ruleAstExpression ) )
            	    {
            	    otherlv_4=(Token)match(input,26,FOLLOW_13); 

            	        	newLeafNode(otherlv_4, grammarAccess.getOutputPatternAccess().getCommaKeyword_4_0());
            	        
            	    // InternalCal.g:2937:1: ( (lv_values_5_0= ruleAstExpression ) )
            	    // InternalCal.g:2938:1: (lv_values_5_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:2938:1: (lv_values_5_0= ruleAstExpression )
            	    // InternalCal.g:2939:3: lv_values_5_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getOutputPatternAccess().getValuesAstExpressionParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_56);
            	    lv_values_5_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getOutputPatternRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"values",
            	            		lv_values_5_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);

            otherlv_6=(Token)match(input,49,FOLLOW_57); 

                	newLeafNode(otherlv_6, grammarAccess.getOutputPatternAccess().getRightSquareBracketKeyword_5());
                
            // InternalCal.g:2959:1: (otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==50) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalCal.g:2959:3: otherlv_7= 'repeat' ( (lv_repeat_8_0= ruleAstExpression ) )
                    {
                    otherlv_7=(Token)match(input,50,FOLLOW_13); 

                        	newLeafNode(otherlv_7, grammarAccess.getOutputPatternAccess().getRepeatKeyword_6_0());
                        
                    // InternalCal.g:2963:1: ( (lv_repeat_8_0= ruleAstExpression ) )
                    // InternalCal.g:2964:1: (lv_repeat_8_0= ruleAstExpression )
                    {
                    // InternalCal.g:2964:1: (lv_repeat_8_0= ruleAstExpression )
                    // InternalCal.g:2965:3: lv_repeat_8_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getOutputPatternAccess().getRepeatAstExpressionParserRuleCall_6_1_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_repeat_8_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getOutputPatternRule());
                    	        }
                           		set(
                           			current, 
                           			"repeat",
                            		lv_repeat_8_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOutputPattern"


    // $ANTLR start "entryRuleGuard"
    // InternalCal.g:2989:1: entryRuleGuard returns [EObject current=null] : iv_ruleGuard= ruleGuard EOF ;
    public final EObject entryRuleGuard() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGuard = null;


        try {
            // InternalCal.g:2990:2: (iv_ruleGuard= ruleGuard EOF )
            // InternalCal.g:2991:2: iv_ruleGuard= ruleGuard EOF
            {
             newCompositeNode(grammarAccess.getGuardRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGuard=ruleGuard();

            state._fsp--;

             current =iv_ruleGuard; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGuard"


    // $ANTLR start "ruleGuard"
    // InternalCal.g:2998:1: ruleGuard returns [EObject current=null] : ( () ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* ) ;
    public final EObject ruleGuard() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_expressions_1_0 = null;

        EObject lv_expressions_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3001:28: ( ( () ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* ) )
            // InternalCal.g:3002:1: ( () ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* )
            {
            // InternalCal.g:3002:1: ( () ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* )
            // InternalCal.g:3002:2: () ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )*
            {
            // InternalCal.g:3002:2: ()
            // InternalCal.g:3003:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getGuardAccess().getGuardAction_0(),
                        current);
                

            }

            // InternalCal.g:3008:2: ( (lv_expressions_1_0= ruleAstExpression ) )
            // InternalCal.g:3009:1: (lv_expressions_1_0= ruleAstExpression )
            {
            // InternalCal.g:3009:1: (lv_expressions_1_0= ruleAstExpression )
            // InternalCal.g:3010:3: lv_expressions_1_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getGuardAccess().getExpressionsAstExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_58);
            lv_expressions_1_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGuardRule());
            	        }
                   		add(
                   			current, 
                   			"expressions",
                    		lv_expressions_1_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:3026:2: (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==26) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // InternalCal.g:3026:4: otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) )
            	    {
            	    otherlv_2=(Token)match(input,26,FOLLOW_13); 

            	        	newLeafNode(otherlv_2, grammarAccess.getGuardAccess().getCommaKeyword_2_0());
            	        
            	    // InternalCal.g:3030:1: ( (lv_expressions_3_0= ruleAstExpression ) )
            	    // InternalCal.g:3031:1: (lv_expressions_3_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:3031:1: (lv_expressions_3_0= ruleAstExpression )
            	    // InternalCal.g:3032:3: lv_expressions_3_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGuardAccess().getExpressionsAstExpressionParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_58);
            	    lv_expressions_3_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGuardRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"expressions",
            	            		lv_expressions_3_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGuard"


    // $ANTLR start "entryRuleStatementAssign"
    // InternalCal.g:3056:1: entryRuleStatementAssign returns [EObject current=null] : iv_ruleStatementAssign= ruleStatementAssign EOF ;
    public final EObject entryRuleStatementAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementAssign = null;


        try {
            // InternalCal.g:3057:2: (iv_ruleStatementAssign= ruleStatementAssign EOF )
            // InternalCal.g:3058:2: iv_ruleStatementAssign= ruleStatementAssign EOF
            {
             newCompositeNode(grammarAccess.getStatementAssignRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementAssign=ruleStatementAssign();

            state._fsp--;

             current =iv_ruleStatementAssign; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementAssign"


    // $ANTLR start "ruleStatementAssign"
    // InternalCal.g:3065:1: ruleStatementAssign returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_target_1_0= ruleVariableReference ) ) (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )* otherlv_5= ':=' ( (lv_value_6_0= ruleAstExpression ) ) otherlv_7= ';' ) ;
    public final EObject ruleStatementAssign() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_target_1_0 = null;

        EObject lv_indexes_3_0 = null;

        EObject lv_value_6_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3068:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_target_1_0= ruleVariableReference ) ) (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )* otherlv_5= ':=' ( (lv_value_6_0= ruleAstExpression ) ) otherlv_7= ';' ) )
            // InternalCal.g:3069:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_target_1_0= ruleVariableReference ) ) (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )* otherlv_5= ':=' ( (lv_value_6_0= ruleAstExpression ) ) otherlv_7= ';' )
            {
            // InternalCal.g:3069:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_target_1_0= ruleVariableReference ) ) (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )* otherlv_5= ':=' ( (lv_value_6_0= ruleAstExpression ) ) otherlv_7= ';' )
            // InternalCal.g:3069:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_target_1_0= ruleVariableReference ) ) (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )* otherlv_5= ':=' ( (lv_value_6_0= ruleAstExpression ) ) otherlv_7= ';'
            {
            // InternalCal.g:3069:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==94) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalCal.g:3070:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:3070:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:3071:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementAssignAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_59);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementAssignRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);

            // InternalCal.g:3087:3: ( (lv_target_1_0= ruleVariableReference ) )
            // InternalCal.g:3088:1: (lv_target_1_0= ruleVariableReference )
            {
            // InternalCal.g:3088:1: (lv_target_1_0= ruleVariableReference )
            // InternalCal.g:3089:3: lv_target_1_0= ruleVariableReference
            {
             
            	        newCompositeNode(grammarAccess.getStatementAssignAccess().getTargetVariableReferenceParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_60);
            lv_target_1_0=ruleVariableReference();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementAssignRule());
            	        }
                   		set(
                   			current, 
                   			"target",
                    		lv_target_1_0, 
                    		"net.sf.orcc.cal.Cal.VariableReference");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:3105:2: (otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']' )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==48) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // InternalCal.g:3105:4: otherlv_2= '[' ( (lv_indexes_3_0= ruleAstExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,48,FOLLOW_13); 

            	        	newLeafNode(otherlv_2, grammarAccess.getStatementAssignAccess().getLeftSquareBracketKeyword_2_0());
            	        
            	    // InternalCal.g:3109:1: ( (lv_indexes_3_0= ruleAstExpression ) )
            	    // InternalCal.g:3110:1: (lv_indexes_3_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:3110:1: (lv_indexes_3_0= ruleAstExpression )
            	    // InternalCal.g:3111:3: lv_indexes_3_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementAssignAccess().getIndexesAstExpressionParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_61);
            	    lv_indexes_3_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementAssignRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"indexes",
            	            		lv_indexes_3_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }

            	    otherlv_4=(Token)match(input,49,FOLLOW_60); 

            	        	newLeafNode(otherlv_4, grammarAccess.getStatementAssignAccess().getRightSquareBracketKeyword_2_2());
            	        

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

            otherlv_5=(Token)match(input,51,FOLLOW_13); 

                	newLeafNode(otherlv_5, grammarAccess.getStatementAssignAccess().getColonEqualsSignKeyword_3());
                
            // InternalCal.g:3135:1: ( (lv_value_6_0= ruleAstExpression ) )
            // InternalCal.g:3136:1: (lv_value_6_0= ruleAstExpression )
            {
            // InternalCal.g:3136:1: (lv_value_6_0= ruleAstExpression )
            // InternalCal.g:3137:3: lv_value_6_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementAssignAccess().getValueAstExpressionParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_4);
            lv_value_6_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementAssignRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_6_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_7, grammarAccess.getStatementAssignAccess().getSemicolonKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementAssign"


    // $ANTLR start "entryRuleStatementCall"
    // InternalCal.g:3165:1: entryRuleStatementCall returns [EObject current=null] : iv_ruleStatementCall= ruleStatementCall EOF ;
    public final EObject entryRuleStatementCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementCall = null;


        try {
            // InternalCal.g:3166:2: (iv_ruleStatementCall= ruleStatementCall EOF )
            // InternalCal.g:3167:2: iv_ruleStatementCall= ruleStatementCall EOF
            {
             newCompositeNode(grammarAccess.getStatementCallRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementCall=ruleStatementCall();

            state._fsp--;

             current =iv_ruleStatementCall; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementCall"


    // $ANTLR start "ruleStatementCall"
    // InternalCal.g:3174:1: ruleStatementCall returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' otherlv_7= ';' ) ;
    public final EObject ruleStatementCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_arguments_3_0 = null;

        EObject lv_arguments_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3177:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' otherlv_7= ';' ) )
            // InternalCal.g:3178:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' otherlv_7= ';' )
            {
            // InternalCal.g:3178:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' otherlv_7= ';' )
            // InternalCal.g:3178:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' otherlv_7= ';'
            {
            // InternalCal.g:3178:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==94) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalCal.g:3179:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:3179:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:3180:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementCallAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_59);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementCallRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);

            // InternalCal.g:3196:3: ( ( ruleQualifiedName ) )
            // InternalCal.g:3197:1: ( ruleQualifiedName )
            {
            // InternalCal.g:3197:1: ( ruleQualifiedName )
            // InternalCal.g:3198:3: ruleQualifiedName
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getStatementCallRule());
            	        }
                    
             
            	        newCompositeNode(grammarAccess.getStatementCallAccess().getProcedureAstProcedureCrossReference_1_0()); 
            	    
            pushFollow(FOLLOW_7);
            ruleQualifiedName();

            state._fsp--;

             
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,25,FOLLOW_62); 

                	newLeafNode(otherlv_2, grammarAccess.getStatementCallAccess().getLeftParenthesisKeyword_2());
                
            // InternalCal.g:3215:1: ( ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )* )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( ((LA71_0>=RULE_ID && LA71_0<=RULE_STRING)||LA71_0==25||LA71_0==48||LA71_0==55||LA71_0==73||(LA71_0>=78 && LA71_0<=80)||(LA71_0>=82 && LA71_0<=83)||LA71_0==94) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalCal.g:3215:2: ( (lv_arguments_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )*
                    {
                    // InternalCal.g:3215:2: ( (lv_arguments_3_0= ruleAstExpression ) )
                    // InternalCal.g:3216:1: (lv_arguments_3_0= ruleAstExpression )
                    {
                    // InternalCal.g:3216:1: (lv_arguments_3_0= ruleAstExpression )
                    // InternalCal.g:3217:3: lv_arguments_3_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getStatementCallAccess().getArgumentsAstExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_arguments_3_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStatementCallRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_3_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:3233:2: (otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) ) )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==26) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // InternalCal.g:3233:4: otherlv_4= ',' ( (lv_arguments_5_0= ruleAstExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,26,FOLLOW_13); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getStatementCallAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // InternalCal.g:3237:1: ( (lv_arguments_5_0= ruleAstExpression ) )
                    	    // InternalCal.g:3238:1: (lv_arguments_5_0= ruleAstExpression )
                    	    {
                    	    // InternalCal.g:3238:1: (lv_arguments_5_0= ruleAstExpression )
                    	    // InternalCal.g:3239:3: lv_arguments_5_0= ruleAstExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getStatementCallAccess().getArgumentsAstExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_arguments_5_0=ruleAstExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getStatementCallRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"arguments",
                    	            		lv_arguments_5_0, 
                    	            		"net.sf.orcc.cal.Cal.AstExpression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,27,FOLLOW_4); 

                	newLeafNode(otherlv_6, grammarAccess.getStatementCallAccess().getRightParenthesisKeyword_4());
                
            otherlv_7=(Token)match(input,16,FOLLOW_2); 

                	newLeafNode(otherlv_7, grammarAccess.getStatementCallAccess().getSemicolonKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementCall"


    // $ANTLR start "entryRuleStatementForeach"
    // InternalCal.g:3271:1: entryRuleStatementForeach returns [EObject current=null] : iv_ruleStatementForeach= ruleStatementForeach EOF ;
    public final EObject entryRuleStatementForeach() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementForeach = null;


        try {
            // InternalCal.g:3272:2: (iv_ruleStatementForeach= ruleStatementForeach EOF )
            // InternalCal.g:3273:2: iv_ruleStatementForeach= ruleStatementForeach EOF
            {
             newCompositeNode(grammarAccess.getStatementForeachRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementForeach=ruleStatementForeach();

            state._fsp--;

             current =iv_ruleStatementForeach; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementForeach"


    // $ANTLR start "ruleStatementForeach"
    // InternalCal.g:3280:1: ruleStatementForeach returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'foreach' ( (lv_variable_2_0= ruleVariableDeclaration ) ) otherlv_3= 'in' ( (lv_lower_4_0= ruleAstExpression ) ) otherlv_5= '..' ( (lv_higher_6_0= ruleAstExpression ) ) otherlv_7= 'do' ( (lv_statements_8_0= ruleStatement ) )* otherlv_9= 'end' ) ;
    public final EObject ruleStatementForeach() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_variable_2_0 = null;

        EObject lv_lower_4_0 = null;

        EObject lv_higher_6_0 = null;

        EObject lv_statements_8_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3283:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'foreach' ( (lv_variable_2_0= ruleVariableDeclaration ) ) otherlv_3= 'in' ( (lv_lower_4_0= ruleAstExpression ) ) otherlv_5= '..' ( (lv_higher_6_0= ruleAstExpression ) ) otherlv_7= 'do' ( (lv_statements_8_0= ruleStatement ) )* otherlv_9= 'end' ) )
            // InternalCal.g:3284:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'foreach' ( (lv_variable_2_0= ruleVariableDeclaration ) ) otherlv_3= 'in' ( (lv_lower_4_0= ruleAstExpression ) ) otherlv_5= '..' ( (lv_higher_6_0= ruleAstExpression ) ) otherlv_7= 'do' ( (lv_statements_8_0= ruleStatement ) )* otherlv_9= 'end' )
            {
            // InternalCal.g:3284:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'foreach' ( (lv_variable_2_0= ruleVariableDeclaration ) ) otherlv_3= 'in' ( (lv_lower_4_0= ruleAstExpression ) ) otherlv_5= '..' ( (lv_higher_6_0= ruleAstExpression ) ) otherlv_7= 'do' ( (lv_statements_8_0= ruleStatement ) )* otherlv_9= 'end' )
            // InternalCal.g:3284:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'foreach' ( (lv_variable_2_0= ruleVariableDeclaration ) ) otherlv_3= 'in' ( (lv_lower_4_0= ruleAstExpression ) ) otherlv_5= '..' ( (lv_higher_6_0= ruleAstExpression ) ) otherlv_7= 'do' ( (lv_statements_8_0= ruleStatement ) )* otherlv_9= 'end'
            {
            // InternalCal.g:3284:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==94) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalCal.g:3285:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:3285:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:3286:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementForeachAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_63);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementForeachRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            otherlv_1=(Token)match(input,52,FOLLOW_16); 

                	newLeafNode(otherlv_1, grammarAccess.getStatementForeachAccess().getForeachKeyword_1());
                
            // InternalCal.g:3306:1: ( (lv_variable_2_0= ruleVariableDeclaration ) )
            // InternalCal.g:3307:1: (lv_variable_2_0= ruleVariableDeclaration )
            {
            // InternalCal.g:3307:1: (lv_variable_2_0= ruleVariableDeclaration )
            // InternalCal.g:3308:3: lv_variable_2_0= ruleVariableDeclaration
            {
             
            	        newCompositeNode(grammarAccess.getStatementForeachAccess().getVariableVariableDeclarationParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_64);
            lv_variable_2_0=ruleVariableDeclaration();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementForeachRule());
            	        }
                   		set(
                   			current, 
                   			"variable",
                    		lv_variable_2_0, 
                    		"net.sf.orcc.cal.Cal.VariableDeclaration");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,53,FOLLOW_13); 

                	newLeafNode(otherlv_3, grammarAccess.getStatementForeachAccess().getInKeyword_3());
                
            // InternalCal.g:3328:1: ( (lv_lower_4_0= ruleAstExpression ) )
            // InternalCal.g:3329:1: (lv_lower_4_0= ruleAstExpression )
            {
            // InternalCal.g:3329:1: (lv_lower_4_0= ruleAstExpression )
            // InternalCal.g:3330:3: lv_lower_4_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementForeachAccess().getLowerAstExpressionParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_65);
            lv_lower_4_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementForeachRule());
            	        }
                   		set(
                   			current, 
                   			"lower",
                    		lv_lower_4_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_5=(Token)match(input,54,FOLLOW_13); 

                	newLeafNode(otherlv_5, grammarAccess.getStatementForeachAccess().getFullStopFullStopKeyword_5());
                
            // InternalCal.g:3350:1: ( (lv_higher_6_0= ruleAstExpression ) )
            // InternalCal.g:3351:1: (lv_higher_6_0= ruleAstExpression )
            {
            // InternalCal.g:3351:1: (lv_higher_6_0= ruleAstExpression )
            // InternalCal.g:3352:3: lv_higher_6_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementForeachAccess().getHigherAstExpressionParserRuleCall_6_0()); 
            	    
            pushFollow(FOLLOW_66);
            lv_higher_6_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementForeachRule());
            	        }
                   		set(
                   			current, 
                   			"higher",
                    		lv_higher_6_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_7=(Token)match(input,46,FOLLOW_30); 

                	newLeafNode(otherlv_7, grammarAccess.getStatementForeachAccess().getDoKeyword_7());
                
            // InternalCal.g:3372:1: ( (lv_statements_8_0= ruleStatement ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==RULE_ID||LA73_0==52||LA73_0==55||LA73_0==59||LA73_0==94) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // InternalCal.g:3373:1: (lv_statements_8_0= ruleStatement )
            	    {
            	    // InternalCal.g:3373:1: (lv_statements_8_0= ruleStatement )
            	    // InternalCal.g:3374:3: lv_statements_8_0= ruleStatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementForeachAccess().getStatementsStatementParserRuleCall_8_0()); 
            	    	    
            	    pushFollow(FOLLOW_30);
            	    lv_statements_8_0=ruleStatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementForeachRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"statements",
            	            		lv_statements_8_0, 
            	            		"net.sf.orcc.cal.Cal.Statement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);

            otherlv_9=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_9, grammarAccess.getStatementForeachAccess().getEndKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementForeach"


    // $ANTLR start "entryRuleStatementIf"
    // InternalCal.g:3402:1: entryRuleStatementIf returns [EObject current=null] : iv_ruleStatementIf= ruleStatementIf EOF ;
    public final EObject entryRuleStatementIf() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementIf = null;


        try {
            // InternalCal.g:3403:2: (iv_ruleStatementIf= ruleStatementIf EOF )
            // InternalCal.g:3404:2: iv_ruleStatementIf= ruleStatementIf EOF
            {
             newCompositeNode(grammarAccess.getStatementIfRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementIf=ruleStatementIf();

            state._fsp--;

             current =iv_ruleStatementIf; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementIf"


    // $ANTLR start "ruleStatementIf"
    // InternalCal.g:3411:1: ruleStatementIf returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'if' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'then' ( (lv_then_4_0= ruleStatement ) )* ( (lv_elsifs_5_0= ruleStatementElsif ) )* (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )? otherlv_8= 'end' ) ;
    public final EObject ruleStatementIf() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_condition_2_0 = null;

        EObject lv_then_4_0 = null;

        EObject lv_elsifs_5_0 = null;

        EObject lv_else_7_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3414:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'if' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'then' ( (lv_then_4_0= ruleStatement ) )* ( (lv_elsifs_5_0= ruleStatementElsif ) )* (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )? otherlv_8= 'end' ) )
            // InternalCal.g:3415:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'if' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'then' ( (lv_then_4_0= ruleStatement ) )* ( (lv_elsifs_5_0= ruleStatementElsif ) )* (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )? otherlv_8= 'end' )
            {
            // InternalCal.g:3415:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'if' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'then' ( (lv_then_4_0= ruleStatement ) )* ( (lv_elsifs_5_0= ruleStatementElsif ) )* (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )? otherlv_8= 'end' )
            // InternalCal.g:3415:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'if' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'then' ( (lv_then_4_0= ruleStatement ) )* ( (lv_elsifs_5_0= ruleStatementElsif ) )* (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )? otherlv_8= 'end'
            {
            // InternalCal.g:3415:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==94) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // InternalCal.g:3416:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:3416:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:3417:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementIfAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_67);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementIfRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop74;
                }
            } while (true);

            otherlv_1=(Token)match(input,55,FOLLOW_13); 

                	newLeafNode(otherlv_1, grammarAccess.getStatementIfAccess().getIfKeyword_1());
                
            // InternalCal.g:3437:1: ( (lv_condition_2_0= ruleAstExpression ) )
            // InternalCal.g:3438:1: (lv_condition_2_0= ruleAstExpression )
            {
            // InternalCal.g:3438:1: (lv_condition_2_0= ruleAstExpression )
            // InternalCal.g:3439:3: lv_condition_2_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementIfAccess().getConditionAstExpressionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_68);
            lv_condition_2_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementIfRule());
            	        }
                   		set(
                   			current, 
                   			"condition",
                    		lv_condition_2_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,56,FOLLOW_69); 

                	newLeafNode(otherlv_3, grammarAccess.getStatementIfAccess().getThenKeyword_3());
                
            // InternalCal.g:3459:1: ( (lv_then_4_0= ruleStatement ) )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==RULE_ID||LA75_0==52||LA75_0==55||LA75_0==59||LA75_0==94) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // InternalCal.g:3460:1: (lv_then_4_0= ruleStatement )
            	    {
            	    // InternalCal.g:3460:1: (lv_then_4_0= ruleStatement )
            	    // InternalCal.g:3461:3: lv_then_4_0= ruleStatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementIfAccess().getThenStatementParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_69);
            	    lv_then_4_0=ruleStatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementIfRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"then",
            	            		lv_then_4_0, 
            	            		"net.sf.orcc.cal.Cal.Statement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);

            // InternalCal.g:3477:3: ( (lv_elsifs_5_0= ruleStatementElsif ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==58) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalCal.g:3478:1: (lv_elsifs_5_0= ruleStatementElsif )
            	    {
            	    // InternalCal.g:3478:1: (lv_elsifs_5_0= ruleStatementElsif )
            	    // InternalCal.g:3479:3: lv_elsifs_5_0= ruleStatementElsif
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementIfAccess().getElsifsStatementElsifParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FOLLOW_70);
            	    lv_elsifs_5_0=ruleStatementElsif();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementIfRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"elsifs",
            	            		lv_elsifs_5_0, 
            	            		"net.sf.orcc.cal.Cal.StatementElsif");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);

            // InternalCal.g:3495:3: (otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )* )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==57) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalCal.g:3495:5: otherlv_6= 'else' ( (lv_else_7_0= ruleStatement ) )*
                    {
                    otherlv_6=(Token)match(input,57,FOLLOW_30); 

                        	newLeafNode(otherlv_6, grammarAccess.getStatementIfAccess().getElseKeyword_6_0());
                        
                    // InternalCal.g:3499:1: ( (lv_else_7_0= ruleStatement ) )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==RULE_ID||LA77_0==52||LA77_0==55||LA77_0==59||LA77_0==94) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // InternalCal.g:3500:1: (lv_else_7_0= ruleStatement )
                    	    {
                    	    // InternalCal.g:3500:1: (lv_else_7_0= ruleStatement )
                    	    // InternalCal.g:3501:3: lv_else_7_0= ruleStatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getStatementIfAccess().getElseStatementParserRuleCall_6_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_30);
                    	    lv_else_7_0=ruleStatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getStatementIfRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"else",
                    	            		lv_else_7_0, 
                    	            		"net.sf.orcc.cal.Cal.Statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop77;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_8=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_8, grammarAccess.getStatementIfAccess().getEndKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementIf"


    // $ANTLR start "entryRuleStatementElsif"
    // InternalCal.g:3529:1: entryRuleStatementElsif returns [EObject current=null] : iv_ruleStatementElsif= ruleStatementElsif EOF ;
    public final EObject entryRuleStatementElsif() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementElsif = null;


        try {
            // InternalCal.g:3530:2: (iv_ruleStatementElsif= ruleStatementElsif EOF )
            // InternalCal.g:3531:2: iv_ruleStatementElsif= ruleStatementElsif EOF
            {
             newCompositeNode(grammarAccess.getStatementElsifRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementElsif=ruleStatementElsif();

            state._fsp--;

             current =iv_ruleStatementElsif; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementElsif"


    // $ANTLR start "ruleStatementElsif"
    // InternalCal.g:3538:1: ruleStatementElsif returns [EObject current=null] : (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleStatement ) )* ) ;
    public final EObject ruleStatementElsif() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_then_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3541:28: ( (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleStatement ) )* ) )
            // InternalCal.g:3542:1: (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleStatement ) )* )
            {
            // InternalCal.g:3542:1: (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleStatement ) )* )
            // InternalCal.g:3542:3: otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleStatement ) )*
            {
            otherlv_0=(Token)match(input,58,FOLLOW_13); 

                	newLeafNode(otherlv_0, grammarAccess.getStatementElsifAccess().getElsifKeyword_0());
                
            // InternalCal.g:3546:1: ( (lv_condition_1_0= ruleAstExpression ) )
            // InternalCal.g:3547:1: (lv_condition_1_0= ruleAstExpression )
            {
            // InternalCal.g:3547:1: (lv_condition_1_0= ruleAstExpression )
            // InternalCal.g:3548:3: lv_condition_1_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementElsifAccess().getConditionAstExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_68);
            lv_condition_1_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementElsifRule());
            	        }
                   		set(
                   			current, 
                   			"condition",
                    		lv_condition_1_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,56,FOLLOW_71); 

                	newLeafNode(otherlv_2, grammarAccess.getStatementElsifAccess().getThenKeyword_2());
                
            // InternalCal.g:3568:1: ( (lv_then_3_0= ruleStatement ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==RULE_ID||LA79_0==52||LA79_0==55||LA79_0==59||LA79_0==94) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalCal.g:3569:1: (lv_then_3_0= ruleStatement )
            	    {
            	    // InternalCal.g:3569:1: (lv_then_3_0= ruleStatement )
            	    // InternalCal.g:3570:3: lv_then_3_0= ruleStatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementElsifAccess().getThenStatementParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_71);
            	    lv_then_3_0=ruleStatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementElsifRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"then",
            	            		lv_then_3_0, 
            	            		"net.sf.orcc.cal.Cal.Statement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementElsif"


    // $ANTLR start "entryRuleStatementWhile"
    // InternalCal.g:3594:1: entryRuleStatementWhile returns [EObject current=null] : iv_ruleStatementWhile= ruleStatementWhile EOF ;
    public final EObject entryRuleStatementWhile() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementWhile = null;


        try {
            // InternalCal.g:3595:2: (iv_ruleStatementWhile= ruleStatementWhile EOF )
            // InternalCal.g:3596:2: iv_ruleStatementWhile= ruleStatementWhile EOF
            {
             newCompositeNode(grammarAccess.getStatementWhileRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementWhile=ruleStatementWhile();

            state._fsp--;

             current =iv_ruleStatementWhile; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementWhile"


    // $ANTLR start "ruleStatementWhile"
    // InternalCal.g:3603:1: ruleStatementWhile returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'while' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'do' ( (lv_statements_4_0= ruleStatement ) )* otherlv_5= 'end' ) ;
    public final EObject ruleStatementWhile() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_condition_2_0 = null;

        EObject lv_statements_4_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3606:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'while' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'do' ( (lv_statements_4_0= ruleStatement ) )* otherlv_5= 'end' ) )
            // InternalCal.g:3607:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'while' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'do' ( (lv_statements_4_0= ruleStatement ) )* otherlv_5= 'end' )
            {
            // InternalCal.g:3607:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'while' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'do' ( (lv_statements_4_0= ruleStatement ) )* otherlv_5= 'end' )
            // InternalCal.g:3607:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* otherlv_1= 'while' ( (lv_condition_2_0= ruleAstExpression ) ) otherlv_3= 'do' ( (lv_statements_4_0= ruleStatement ) )* otherlv_5= 'end'
            {
            // InternalCal.g:3607:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==94) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // InternalCal.g:3608:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:3608:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:3609:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementWhileAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_72);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementWhileRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);

            otherlv_1=(Token)match(input,59,FOLLOW_13); 

                	newLeafNode(otherlv_1, grammarAccess.getStatementWhileAccess().getWhileKeyword_1());
                
            // InternalCal.g:3629:1: ( (lv_condition_2_0= ruleAstExpression ) )
            // InternalCal.g:3630:1: (lv_condition_2_0= ruleAstExpression )
            {
            // InternalCal.g:3630:1: (lv_condition_2_0= ruleAstExpression )
            // InternalCal.g:3631:3: lv_condition_2_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getStatementWhileAccess().getConditionAstExpressionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_66);
            lv_condition_2_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStatementWhileRule());
            	        }
                   		set(
                   			current, 
                   			"condition",
                    		lv_condition_2_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,46,FOLLOW_30); 

                	newLeafNode(otherlv_3, grammarAccess.getStatementWhileAccess().getDoKeyword_3());
                
            // InternalCal.g:3651:1: ( (lv_statements_4_0= ruleStatement ) )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==RULE_ID||LA81_0==52||LA81_0==55||LA81_0==59||LA81_0==94) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // InternalCal.g:3652:1: (lv_statements_4_0= ruleStatement )
            	    {
            	    // InternalCal.g:3652:1: (lv_statements_4_0= ruleStatement )
            	    // InternalCal.g:3653:3: lv_statements_4_0= ruleStatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStatementWhileAccess().getStatementsStatementParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_30);
            	    lv_statements_4_0=ruleStatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStatementWhileRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"statements",
            	            		lv_statements_4_0, 
            	            		"net.sf.orcc.cal.Cal.Statement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);

            otherlv_5=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_5, grammarAccess.getStatementWhileAccess().getEndKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementWhile"


    // $ANTLR start "entryRuleStatement"
    // InternalCal.g:3681:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalCal.g:3682:2: (iv_ruleStatement= ruleStatement EOF )
            // InternalCal.g:3683:2: iv_ruleStatement= ruleStatement EOF
            {
             newCompositeNode(grammarAccess.getStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;

             current =iv_ruleStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalCal.g:3690:1: ruleStatement returns [EObject current=null] : (this_StatementAssign_0= ruleStatementAssign | this_StatementCall_1= ruleStatementCall | this_StatementForeach_2= ruleStatementForeach | this_StatementIf_3= ruleStatementIf | this_StatementWhile_4= ruleStatementWhile ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        EObject this_StatementAssign_0 = null;

        EObject this_StatementCall_1 = null;

        EObject this_StatementForeach_2 = null;

        EObject this_StatementIf_3 = null;

        EObject this_StatementWhile_4 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3693:28: ( (this_StatementAssign_0= ruleStatementAssign | this_StatementCall_1= ruleStatementCall | this_StatementForeach_2= ruleStatementForeach | this_StatementIf_3= ruleStatementIf | this_StatementWhile_4= ruleStatementWhile ) )
            // InternalCal.g:3694:1: (this_StatementAssign_0= ruleStatementAssign | this_StatementCall_1= ruleStatementCall | this_StatementForeach_2= ruleStatementForeach | this_StatementIf_3= ruleStatementIf | this_StatementWhile_4= ruleStatementWhile )
            {
            // InternalCal.g:3694:1: (this_StatementAssign_0= ruleStatementAssign | this_StatementCall_1= ruleStatementCall | this_StatementForeach_2= ruleStatementForeach | this_StatementIf_3= ruleStatementIf | this_StatementWhile_4= ruleStatementWhile )
            int alt82=5;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // InternalCal.g:3695:5: this_StatementAssign_0= ruleStatementAssign
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getStatementAssignParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_2);
                    this_StatementAssign_0=ruleStatementAssign();

                    state._fsp--;

                     
                            current = this_StatementAssign_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:3705:5: this_StatementCall_1= ruleStatementCall
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getStatementCallParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_2);
                    this_StatementCall_1=ruleStatementCall();

                    state._fsp--;

                     
                            current = this_StatementCall_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // InternalCal.g:3715:5: this_StatementForeach_2= ruleStatementForeach
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getStatementForeachParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_2);
                    this_StatementForeach_2=ruleStatementForeach();

                    state._fsp--;

                     
                            current = this_StatementForeach_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // InternalCal.g:3725:5: this_StatementIf_3= ruleStatementIf
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getStatementIfParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_2);
                    this_StatementIf_3=ruleStatementIf();

                    state._fsp--;

                     
                            current = this_StatementIf_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // InternalCal.g:3735:5: this_StatementWhile_4= ruleStatementWhile
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getStatementWhileParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_2);
                    this_StatementWhile_4=ruleStatementWhile();

                    state._fsp--;

                     
                            current = this_StatementWhile_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleAstExpression"
    // InternalCal.g:3751:1: entryRuleAstExpression returns [EObject current=null] : iv_ruleAstExpression= ruleAstExpression EOF ;
    public final EObject entryRuleAstExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstExpression = null;


        try {
            // InternalCal.g:3752:2: (iv_ruleAstExpression= ruleAstExpression EOF )
            // InternalCal.g:3753:2: iv_ruleAstExpression= ruleAstExpression EOF
            {
             newCompositeNode(grammarAccess.getAstExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstExpression=ruleAstExpression();

            state._fsp--;

             current =iv_ruleAstExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstExpression"


    // $ANTLR start "ruleAstExpression"
    // InternalCal.g:3760:1: ruleAstExpression returns [EObject current=null] : (this_ExpressionAnd_0= ruleExpressionAnd ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )* ) ;
    public final EObject ruleAstExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        EObject this_ExpressionAnd_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3763:28: ( (this_ExpressionAnd_0= ruleExpressionAnd ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )* ) )
            // InternalCal.g:3764:1: (this_ExpressionAnd_0= ruleExpressionAnd ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )* )
            {
            // InternalCal.g:3764:1: (this_ExpressionAnd_0= ruleExpressionAnd ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )* )
            // InternalCal.g:3765:5: this_ExpressionAnd_0= ruleExpressionAnd ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAstExpressionAccess().getExpressionAndParserRuleCall_0()); 
                
            pushFollow(FOLLOW_73);
            this_ExpressionAnd_0=ruleExpressionAnd();

            state._fsp--;

             
                    current = this_ExpressionAnd_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:3773:1: ( () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( ((LA84_0>=60 && LA84_0<=61)) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalCal.g:3773:2: () ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) ) ( (lv_right_3_0= ruleExpressionAnd ) )
            	    {
            	    // InternalCal.g:3773:2: ()
            	    // InternalCal.g:3774:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getAstExpressionAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:3779:2: ( ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) ) )
            	    // InternalCal.g:3780:1: ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) )
            	    {
            	    // InternalCal.g:3780:1: ( (lv_operator_2_1= '||' | lv_operator_2_2= 'or' ) )
            	    // InternalCal.g:3781:1: (lv_operator_2_1= '||' | lv_operator_2_2= 'or' )
            	    {
            	    // InternalCal.g:3781:1: (lv_operator_2_1= '||' | lv_operator_2_2= 'or' )
            	    int alt83=2;
            	    int LA83_0 = input.LA(1);

            	    if ( (LA83_0==60) ) {
            	        alt83=1;
            	    }
            	    else if ( (LA83_0==61) ) {
            	        alt83=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 83, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt83) {
            	        case 1 :
            	            // InternalCal.g:3782:3: lv_operator_2_1= '||'
            	            {
            	            lv_operator_2_1=(Token)match(input,60,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getAstExpressionAccess().getOperatorVerticalLineVerticalLineKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getAstExpressionRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:3794:8: lv_operator_2_2= 'or'
            	            {
            	            lv_operator_2_2=(Token)match(input,61,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getAstExpressionAccess().getOperatorOrKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getAstExpressionRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:3809:2: ( (lv_right_3_0= ruleExpressionAnd ) )
            	    // InternalCal.g:3810:1: (lv_right_3_0= ruleExpressionAnd )
            	    {
            	    // InternalCal.g:3810:1: (lv_right_3_0= ruleExpressionAnd )
            	    // InternalCal.g:3811:3: lv_right_3_0= ruleExpressionAnd
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAstExpressionAccess().getRightExpressionAndParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_73);
            	    lv_right_3_0=ruleExpressionAnd();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAstExpressionRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionAnd");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstExpression"


    // $ANTLR start "entryRuleExpressionAnd"
    // InternalCal.g:3835:1: entryRuleExpressionAnd returns [EObject current=null] : iv_ruleExpressionAnd= ruleExpressionAnd EOF ;
    public final EObject entryRuleExpressionAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionAnd = null;


        try {
            // InternalCal.g:3836:2: (iv_ruleExpressionAnd= ruleExpressionAnd EOF )
            // InternalCal.g:3837:2: iv_ruleExpressionAnd= ruleExpressionAnd EOF
            {
             newCompositeNode(grammarAccess.getExpressionAndRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionAnd=ruleExpressionAnd();

            state._fsp--;

             current =iv_ruleExpressionAnd; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionAnd"


    // $ANTLR start "ruleExpressionAnd"
    // InternalCal.g:3844:1: ruleExpressionAnd returns [EObject current=null] : (this_ExpressionBitor_0= ruleExpressionBitor ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )* ) ;
    public final EObject ruleExpressionAnd() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        EObject this_ExpressionBitor_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3847:28: ( (this_ExpressionBitor_0= ruleExpressionBitor ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )* ) )
            // InternalCal.g:3848:1: (this_ExpressionBitor_0= ruleExpressionBitor ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )* )
            {
            // InternalCal.g:3848:1: (this_ExpressionBitor_0= ruleExpressionBitor ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )* )
            // InternalCal.g:3849:5: this_ExpressionBitor_0= ruleExpressionBitor ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionAndAccess().getExpressionBitorParserRuleCall_0()); 
                
            pushFollow(FOLLOW_74);
            this_ExpressionBitor_0=ruleExpressionBitor();

            state._fsp--;

             
                    current = this_ExpressionBitor_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:3857:1: ( () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) ) )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( ((LA86_0>=62 && LA86_0<=63)) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // InternalCal.g:3857:2: () ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) ) ( (lv_right_3_0= ruleExpressionBitor ) )
            	    {
            	    // InternalCal.g:3857:2: ()
            	    // InternalCal.g:3858:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionAndAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:3863:2: ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) ) )
            	    // InternalCal.g:3864:1: ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) )
            	    {
            	    // InternalCal.g:3864:1: ( (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' ) )
            	    // InternalCal.g:3865:1: (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' )
            	    {
            	    // InternalCal.g:3865:1: (lv_operator_2_1= '&&' | lv_operator_2_2= 'and' )
            	    int alt85=2;
            	    int LA85_0 = input.LA(1);

            	    if ( (LA85_0==62) ) {
            	        alt85=1;
            	    }
            	    else if ( (LA85_0==63) ) {
            	        alt85=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 85, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt85) {
            	        case 1 :
            	            // InternalCal.g:3866:3: lv_operator_2_1= '&&'
            	            {
            	            lv_operator_2_1=(Token)match(input,62,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionAndAccess().getOperatorAmpersandAmpersandKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionAndRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:3878:8: lv_operator_2_2= 'and'
            	            {
            	            lv_operator_2_2=(Token)match(input,63,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionAndAccess().getOperatorAndKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionAndRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:3893:2: ( (lv_right_3_0= ruleExpressionBitor ) )
            	    // InternalCal.g:3894:1: (lv_right_3_0= ruleExpressionBitor )
            	    {
            	    // InternalCal.g:3894:1: (lv_right_3_0= ruleExpressionBitor )
            	    // InternalCal.g:3895:3: lv_right_3_0= ruleExpressionBitor
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionAndAccess().getRightExpressionBitorParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_74);
            	    lv_right_3_0=ruleExpressionBitor();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionAndRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionBitor");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionAnd"


    // $ANTLR start "entryRuleExpressionBitor"
    // InternalCal.g:3919:1: entryRuleExpressionBitor returns [EObject current=null] : iv_ruleExpressionBitor= ruleExpressionBitor EOF ;
    public final EObject entryRuleExpressionBitor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionBitor = null;


        try {
            // InternalCal.g:3920:2: (iv_ruleExpressionBitor= ruleExpressionBitor EOF )
            // InternalCal.g:3921:2: iv_ruleExpressionBitor= ruleExpressionBitor EOF
            {
             newCompositeNode(grammarAccess.getExpressionBitorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionBitor=ruleExpressionBitor();

            state._fsp--;

             current =iv_ruleExpressionBitor; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionBitor"


    // $ANTLR start "ruleExpressionBitor"
    // InternalCal.g:3928:1: ruleExpressionBitor returns [EObject current=null] : (this_ExpressionBitxor_0= ruleExpressionBitxor ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )* ) ;
    public final EObject ruleExpressionBitor() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_0=null;
        EObject this_ExpressionBitxor_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:3931:28: ( (this_ExpressionBitxor_0= ruleExpressionBitxor ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )* ) )
            // InternalCal.g:3932:1: (this_ExpressionBitxor_0= ruleExpressionBitxor ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )* )
            {
            // InternalCal.g:3932:1: (this_ExpressionBitxor_0= ruleExpressionBitxor ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )* )
            // InternalCal.g:3933:5: this_ExpressionBitxor_0= ruleExpressionBitxor ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionBitorAccess().getExpressionBitxorParserRuleCall_0()); 
                
            pushFollow(FOLLOW_42);
            this_ExpressionBitxor_0=ruleExpressionBitxor();

            state._fsp--;

             
                    current = this_ExpressionBitxor_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:3941:1: ( () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==40) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalCal.g:3941:2: () ( (lv_operator_2_0= '|' ) ) ( (lv_right_3_0= ruleExpressionBitxor ) )
            	    {
            	    // InternalCal.g:3941:2: ()
            	    // InternalCal.g:3942:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionBitorAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:3947:2: ( (lv_operator_2_0= '|' ) )
            	    // InternalCal.g:3948:1: (lv_operator_2_0= '|' )
            	    {
            	    // InternalCal.g:3948:1: (lv_operator_2_0= '|' )
            	    // InternalCal.g:3949:3: lv_operator_2_0= '|'
            	    {
            	    lv_operator_2_0=(Token)match(input,40,FOLLOW_13); 

            	            newLeafNode(lv_operator_2_0, grammarAccess.getExpressionBitorAccess().getOperatorVerticalLineKeyword_1_1_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getExpressionBitorRule());
            	    	        }
            	           		setWithLastConsumed(current, "operator", lv_operator_2_0, "|");
            	    	    

            	    }


            	    }

            	    // InternalCal.g:3962:2: ( (lv_right_3_0= ruleExpressionBitxor ) )
            	    // InternalCal.g:3963:1: (lv_right_3_0= ruleExpressionBitxor )
            	    {
            	    // InternalCal.g:3963:1: (lv_right_3_0= ruleExpressionBitxor )
            	    // InternalCal.g:3964:3: lv_right_3_0= ruleExpressionBitxor
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionBitorAccess().getRightExpressionBitxorParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_42);
            	    lv_right_3_0=ruleExpressionBitxor();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionBitorRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionBitxor");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionBitor"


    // $ANTLR start "entryRuleExpressionBitxor"
    // InternalCal.g:3988:1: entryRuleExpressionBitxor returns [EObject current=null] : iv_ruleExpressionBitxor= ruleExpressionBitxor EOF ;
    public final EObject entryRuleExpressionBitxor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionBitxor = null;


        try {
            // InternalCal.g:3989:2: (iv_ruleExpressionBitxor= ruleExpressionBitxor EOF )
            // InternalCal.g:3990:2: iv_ruleExpressionBitxor= ruleExpressionBitxor EOF
            {
             newCompositeNode(grammarAccess.getExpressionBitxorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionBitxor=ruleExpressionBitxor();

            state._fsp--;

             current =iv_ruleExpressionBitxor; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionBitxor"


    // $ANTLR start "ruleExpressionBitxor"
    // InternalCal.g:3997:1: ruleExpressionBitxor returns [EObject current=null] : (this_ExpressionBitand_0= ruleExpressionBitand ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )* ) ;
    public final EObject ruleExpressionBitxor() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_0=null;
        EObject this_ExpressionBitand_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4000:28: ( (this_ExpressionBitand_0= ruleExpressionBitand ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )* ) )
            // InternalCal.g:4001:1: (this_ExpressionBitand_0= ruleExpressionBitand ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )* )
            {
            // InternalCal.g:4001:1: (this_ExpressionBitand_0= ruleExpressionBitand ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )* )
            // InternalCal.g:4002:5: this_ExpressionBitand_0= ruleExpressionBitand ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionBitxorAccess().getExpressionBitandParserRuleCall_0()); 
                
            pushFollow(FOLLOW_75);
            this_ExpressionBitand_0=ruleExpressionBitand();

            state._fsp--;

             
                    current = this_ExpressionBitand_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4010:1: ( () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==64) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalCal.g:4010:2: () ( (lv_operator_2_0= '^' ) ) ( (lv_right_3_0= ruleExpressionBitand ) )
            	    {
            	    // InternalCal.g:4010:2: ()
            	    // InternalCal.g:4011:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionBitxorAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4016:2: ( (lv_operator_2_0= '^' ) )
            	    // InternalCal.g:4017:1: (lv_operator_2_0= '^' )
            	    {
            	    // InternalCal.g:4017:1: (lv_operator_2_0= '^' )
            	    // InternalCal.g:4018:3: lv_operator_2_0= '^'
            	    {
            	    lv_operator_2_0=(Token)match(input,64,FOLLOW_13); 

            	            newLeafNode(lv_operator_2_0, grammarAccess.getExpressionBitxorAccess().getOperatorCircumflexAccentKeyword_1_1_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getExpressionBitxorRule());
            	    	        }
            	           		setWithLastConsumed(current, "operator", lv_operator_2_0, "^");
            	    	    

            	    }


            	    }

            	    // InternalCal.g:4031:2: ( (lv_right_3_0= ruleExpressionBitand ) )
            	    // InternalCal.g:4032:1: (lv_right_3_0= ruleExpressionBitand )
            	    {
            	    // InternalCal.g:4032:1: (lv_right_3_0= ruleExpressionBitand )
            	    // InternalCal.g:4033:3: lv_right_3_0= ruleExpressionBitand
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionBitxorAccess().getRightExpressionBitandParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_75);
            	    lv_right_3_0=ruleExpressionBitand();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionBitxorRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionBitand");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionBitxor"


    // $ANTLR start "entryRuleExpressionBitand"
    // InternalCal.g:4057:1: entryRuleExpressionBitand returns [EObject current=null] : iv_ruleExpressionBitand= ruleExpressionBitand EOF ;
    public final EObject entryRuleExpressionBitand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionBitand = null;


        try {
            // InternalCal.g:4058:2: (iv_ruleExpressionBitand= ruleExpressionBitand EOF )
            // InternalCal.g:4059:2: iv_ruleExpressionBitand= ruleExpressionBitand EOF
            {
             newCompositeNode(grammarAccess.getExpressionBitandRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionBitand=ruleExpressionBitand();

            state._fsp--;

             current =iv_ruleExpressionBitand; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionBitand"


    // $ANTLR start "ruleExpressionBitand"
    // InternalCal.g:4066:1: ruleExpressionBitand returns [EObject current=null] : (this_ExpressionEq_0= ruleExpressionEq ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )* ) ;
    public final EObject ruleExpressionBitand() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_0=null;
        EObject this_ExpressionEq_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4069:28: ( (this_ExpressionEq_0= ruleExpressionEq ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )* ) )
            // InternalCal.g:4070:1: (this_ExpressionEq_0= ruleExpressionEq ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )* )
            {
            // InternalCal.g:4070:1: (this_ExpressionEq_0= ruleExpressionEq ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )* )
            // InternalCal.g:4071:5: this_ExpressionEq_0= ruleExpressionEq ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionBitandAccess().getExpressionEqParserRuleCall_0()); 
                
            pushFollow(FOLLOW_76);
            this_ExpressionEq_0=ruleExpressionEq();

            state._fsp--;

             
                    current = this_ExpressionEq_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4079:1: ( () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==65) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalCal.g:4079:2: () ( (lv_operator_2_0= '&' ) ) ( (lv_right_3_0= ruleExpressionEq ) )
            	    {
            	    // InternalCal.g:4079:2: ()
            	    // InternalCal.g:4080:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionBitandAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4085:2: ( (lv_operator_2_0= '&' ) )
            	    // InternalCal.g:4086:1: (lv_operator_2_0= '&' )
            	    {
            	    // InternalCal.g:4086:1: (lv_operator_2_0= '&' )
            	    // InternalCal.g:4087:3: lv_operator_2_0= '&'
            	    {
            	    lv_operator_2_0=(Token)match(input,65,FOLLOW_13); 

            	            newLeafNode(lv_operator_2_0, grammarAccess.getExpressionBitandAccess().getOperatorAmpersandKeyword_1_1_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getExpressionBitandRule());
            	    	        }
            	           		setWithLastConsumed(current, "operator", lv_operator_2_0, "&");
            	    	    

            	    }


            	    }

            	    // InternalCal.g:4100:2: ( (lv_right_3_0= ruleExpressionEq ) )
            	    // InternalCal.g:4101:1: (lv_right_3_0= ruleExpressionEq )
            	    {
            	    // InternalCal.g:4101:1: (lv_right_3_0= ruleExpressionEq )
            	    // InternalCal.g:4102:3: lv_right_3_0= ruleExpressionEq
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionBitandAccess().getRightExpressionEqParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_76);
            	    lv_right_3_0=ruleExpressionEq();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionBitandRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionEq");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionBitand"


    // $ANTLR start "entryRuleExpressionEq"
    // InternalCal.g:4126:1: entryRuleExpressionEq returns [EObject current=null] : iv_ruleExpressionEq= ruleExpressionEq EOF ;
    public final EObject entryRuleExpressionEq() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionEq = null;


        try {
            // InternalCal.g:4127:2: (iv_ruleExpressionEq= ruleExpressionEq EOF )
            // InternalCal.g:4128:2: iv_ruleExpressionEq= ruleExpressionEq EOF
            {
             newCompositeNode(grammarAccess.getExpressionEqRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionEq=ruleExpressionEq();

            state._fsp--;

             current =iv_ruleExpressionEq; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionEq"


    // $ANTLR start "ruleExpressionEq"
    // InternalCal.g:4135:1: ruleExpressionEq returns [EObject current=null] : (this_ExpressionRelational_0= ruleExpressionRelational ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )* ) ;
    public final EObject ruleExpressionEq() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        EObject this_ExpressionRelational_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4138:28: ( (this_ExpressionRelational_0= ruleExpressionRelational ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )* ) )
            // InternalCal.g:4139:1: (this_ExpressionRelational_0= ruleExpressionRelational ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )* )
            {
            // InternalCal.g:4139:1: (this_ExpressionRelational_0= ruleExpressionRelational ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )* )
            // InternalCal.g:4140:5: this_ExpressionRelational_0= ruleExpressionRelational ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionEqAccess().getExpressionRelationalParserRuleCall_0()); 
                
            pushFollow(FOLLOW_77);
            this_ExpressionRelational_0=ruleExpressionRelational();

            state._fsp--;

             
                    current = this_ExpressionRelational_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4148:1: ( () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==24||LA91_0==66) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalCal.g:4148:2: () ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleExpressionRelational ) )
            	    {
            	    // InternalCal.g:4148:2: ()
            	    // InternalCal.g:4149:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionEqAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4154:2: ( ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) ) )
            	    // InternalCal.g:4155:1: ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) )
            	    {
            	    // InternalCal.g:4155:1: ( (lv_operator_2_1= '=' | lv_operator_2_2= '!=' ) )
            	    // InternalCal.g:4156:1: (lv_operator_2_1= '=' | lv_operator_2_2= '!=' )
            	    {
            	    // InternalCal.g:4156:1: (lv_operator_2_1= '=' | lv_operator_2_2= '!=' )
            	    int alt90=2;
            	    int LA90_0 = input.LA(1);

            	    if ( (LA90_0==24) ) {
            	        alt90=1;
            	    }
            	    else if ( (LA90_0==66) ) {
            	        alt90=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 90, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt90) {
            	        case 1 :
            	            // InternalCal.g:4157:3: lv_operator_2_1= '='
            	            {
            	            lv_operator_2_1=(Token)match(input,24,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionEqAccess().getOperatorEqualsSignKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionEqRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:4169:8: lv_operator_2_2= '!='
            	            {
            	            lv_operator_2_2=(Token)match(input,66,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionEqAccess().getOperatorExclamationMarkEqualsSignKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionEqRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:4184:2: ( (lv_right_3_0= ruleExpressionRelational ) )
            	    // InternalCal.g:4185:1: (lv_right_3_0= ruleExpressionRelational )
            	    {
            	    // InternalCal.g:4185:1: (lv_right_3_0= ruleExpressionRelational )
            	    // InternalCal.g:4186:3: lv_right_3_0= ruleExpressionRelational
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionEqAccess().getRightExpressionRelationalParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_77);
            	    lv_right_3_0=ruleExpressionRelational();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionEqRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionRelational");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionEq"


    // $ANTLR start "entryRuleExpressionRelational"
    // InternalCal.g:4210:1: entryRuleExpressionRelational returns [EObject current=null] : iv_ruleExpressionRelational= ruleExpressionRelational EOF ;
    public final EObject entryRuleExpressionRelational() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionRelational = null;


        try {
            // InternalCal.g:4211:2: (iv_ruleExpressionRelational= ruleExpressionRelational EOF )
            // InternalCal.g:4212:2: iv_ruleExpressionRelational= ruleExpressionRelational EOF
            {
             newCompositeNode(grammarAccess.getExpressionRelationalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionRelational=ruleExpressionRelational();

            state._fsp--;

             current =iv_ruleExpressionRelational; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionRelational"


    // $ANTLR start "ruleExpressionRelational"
    // InternalCal.g:4219:1: ruleExpressionRelational returns [EObject current=null] : (this_ExpressionShift_0= ruleExpressionShift ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )* ) ;
    public final EObject ruleExpressionRelational() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        Token lv_operator_2_3=null;
        Token lv_operator_2_4=null;
        EObject this_ExpressionShift_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4222:28: ( (this_ExpressionShift_0= ruleExpressionShift ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )* ) )
            // InternalCal.g:4223:1: (this_ExpressionShift_0= ruleExpressionShift ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )* )
            {
            // InternalCal.g:4223:1: (this_ExpressionShift_0= ruleExpressionShift ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )* )
            // InternalCal.g:4224:5: this_ExpressionShift_0= ruleExpressionShift ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionRelationalAccess().getExpressionShiftParserRuleCall_0()); 
                
            pushFollow(FOLLOW_78);
            this_ExpressionShift_0=ruleExpressionShift();

            state._fsp--;

             
                    current = this_ExpressionShift_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4232:1: ( () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==34||(LA93_0>=67 && LA93_0<=69)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalCal.g:4232:2: () ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) ) ( (lv_right_3_0= ruleExpressionShift ) )
            	    {
            	    // InternalCal.g:4232:2: ()
            	    // InternalCal.g:4233:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionRelationalAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4238:2: ( ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) ) )
            	    // InternalCal.g:4239:1: ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) )
            	    {
            	    // InternalCal.g:4239:1: ( (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' ) )
            	    // InternalCal.g:4240:1: (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' )
            	    {
            	    // InternalCal.g:4240:1: (lv_operator_2_1= '<' | lv_operator_2_2= '<=' | lv_operator_2_3= '>' | lv_operator_2_4= '>=' )
            	    int alt92=4;
            	    switch ( input.LA(1) ) {
            	    case 67:
            	        {
            	        alt92=1;
            	        }
            	        break;
            	    case 68:
            	        {
            	        alt92=2;
            	        }
            	        break;
            	    case 34:
            	        {
            	        alt92=3;
            	        }
            	        break;
            	    case 69:
            	        {
            	        alt92=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 92, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt92) {
            	        case 1 :
            	            // InternalCal.g:4241:3: lv_operator_2_1= '<'
            	            {
            	            lv_operator_2_1=(Token)match(input,67,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionRelationalAccess().getOperatorLessThanSignKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionRelationalRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:4253:8: lv_operator_2_2= '<='
            	            {
            	            lv_operator_2_2=(Token)match(input,68,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionRelationalAccess().getOperatorLessThanSignEqualsSignKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionRelationalRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;
            	        case 3 :
            	            // InternalCal.g:4265:8: lv_operator_2_3= '>'
            	            {
            	            lv_operator_2_3=(Token)match(input,34,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_3, grammarAccess.getExpressionRelationalAccess().getOperatorGreaterThanSignKeyword_1_1_0_2());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionRelationalRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_3, null);
            	            	    

            	            }
            	            break;
            	        case 4 :
            	            // InternalCal.g:4277:8: lv_operator_2_4= '>='
            	            {
            	            lv_operator_2_4=(Token)match(input,69,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_4, grammarAccess.getExpressionRelationalAccess().getOperatorGreaterThanSignEqualsSignKeyword_1_1_0_3());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionRelationalRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_4, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:4292:2: ( (lv_right_3_0= ruleExpressionShift ) )
            	    // InternalCal.g:4293:1: (lv_right_3_0= ruleExpressionShift )
            	    {
            	    // InternalCal.g:4293:1: (lv_right_3_0= ruleExpressionShift )
            	    // InternalCal.g:4294:3: lv_right_3_0= ruleExpressionShift
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionRelationalAccess().getRightExpressionShiftParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_78);
            	    lv_right_3_0=ruleExpressionShift();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionRelationalRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionShift");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionRelational"


    // $ANTLR start "entryRuleExpressionShift"
    // InternalCal.g:4318:1: entryRuleExpressionShift returns [EObject current=null] : iv_ruleExpressionShift= ruleExpressionShift EOF ;
    public final EObject entryRuleExpressionShift() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionShift = null;


        try {
            // InternalCal.g:4319:2: (iv_ruleExpressionShift= ruleExpressionShift EOF )
            // InternalCal.g:4320:2: iv_ruleExpressionShift= ruleExpressionShift EOF
            {
             newCompositeNode(grammarAccess.getExpressionShiftRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionShift=ruleExpressionShift();

            state._fsp--;

             current =iv_ruleExpressionShift; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionShift"


    // $ANTLR start "ruleExpressionShift"
    // InternalCal.g:4327:1: ruleExpressionShift returns [EObject current=null] : (this_ExpressionAdditive_0= ruleExpressionAdditive ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )* ) ;
    public final EObject ruleExpressionShift() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        EObject this_ExpressionAdditive_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4330:28: ( (this_ExpressionAdditive_0= ruleExpressionAdditive ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )* ) )
            // InternalCal.g:4331:1: (this_ExpressionAdditive_0= ruleExpressionAdditive ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )* )
            {
            // InternalCal.g:4331:1: (this_ExpressionAdditive_0= ruleExpressionAdditive ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )* )
            // InternalCal.g:4332:5: this_ExpressionAdditive_0= ruleExpressionAdditive ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionShiftAccess().getExpressionAdditiveParserRuleCall_0()); 
                
            pushFollow(FOLLOW_79);
            this_ExpressionAdditive_0=ruleExpressionAdditive();

            state._fsp--;

             
                    current = this_ExpressionAdditive_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4340:1: ( () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) ) )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=70 && LA95_0<=71)) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // InternalCal.g:4340:2: () ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleExpressionAdditive ) )
            	    {
            	    // InternalCal.g:4340:2: ()
            	    // InternalCal.g:4341:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionShiftAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4346:2: ( ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) ) )
            	    // InternalCal.g:4347:1: ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) )
            	    {
            	    // InternalCal.g:4347:1: ( (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' ) )
            	    // InternalCal.g:4348:1: (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' )
            	    {
            	    // InternalCal.g:4348:1: (lv_operator_2_1= '<<' | lv_operator_2_2= '>>' )
            	    int alt94=2;
            	    int LA94_0 = input.LA(1);

            	    if ( (LA94_0==70) ) {
            	        alt94=1;
            	    }
            	    else if ( (LA94_0==71) ) {
            	        alt94=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 94, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt94) {
            	        case 1 :
            	            // InternalCal.g:4349:3: lv_operator_2_1= '<<'
            	            {
            	            lv_operator_2_1=(Token)match(input,70,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionShiftAccess().getOperatorLessThanSignLessThanSignKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionShiftRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:4361:8: lv_operator_2_2= '>>'
            	            {
            	            lv_operator_2_2=(Token)match(input,71,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionShiftAccess().getOperatorGreaterThanSignGreaterThanSignKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionShiftRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:4376:2: ( (lv_right_3_0= ruleExpressionAdditive ) )
            	    // InternalCal.g:4377:1: (lv_right_3_0= ruleExpressionAdditive )
            	    {
            	    // InternalCal.g:4377:1: (lv_right_3_0= ruleExpressionAdditive )
            	    // InternalCal.g:4378:3: lv_right_3_0= ruleExpressionAdditive
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionShiftAccess().getRightExpressionAdditiveParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_79);
            	    lv_right_3_0=ruleExpressionAdditive();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionShiftRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionAdditive");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionShift"


    // $ANTLR start "entryRuleExpressionAdditive"
    // InternalCal.g:4402:1: entryRuleExpressionAdditive returns [EObject current=null] : iv_ruleExpressionAdditive= ruleExpressionAdditive EOF ;
    public final EObject entryRuleExpressionAdditive() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionAdditive = null;


        try {
            // InternalCal.g:4403:2: (iv_ruleExpressionAdditive= ruleExpressionAdditive EOF )
            // InternalCal.g:4404:2: iv_ruleExpressionAdditive= ruleExpressionAdditive EOF
            {
             newCompositeNode(grammarAccess.getExpressionAdditiveRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionAdditive=ruleExpressionAdditive();

            state._fsp--;

             current =iv_ruleExpressionAdditive; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionAdditive"


    // $ANTLR start "ruleExpressionAdditive"
    // InternalCal.g:4411:1: ruleExpressionAdditive returns [EObject current=null] : (this_ExpressionMultiplicative_0= ruleExpressionMultiplicative ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )* ) ;
    public final EObject ruleExpressionAdditive() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        EObject this_ExpressionMultiplicative_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4414:28: ( (this_ExpressionMultiplicative_0= ruleExpressionMultiplicative ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )* ) )
            // InternalCal.g:4415:1: (this_ExpressionMultiplicative_0= ruleExpressionMultiplicative ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )* )
            {
            // InternalCal.g:4415:1: (this_ExpressionMultiplicative_0= ruleExpressionMultiplicative ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )* )
            // InternalCal.g:4416:5: this_ExpressionMultiplicative_0= ruleExpressionMultiplicative ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionAdditiveAccess().getExpressionMultiplicativeParserRuleCall_0()); 
                
            pushFollow(FOLLOW_80);
            this_ExpressionMultiplicative_0=ruleExpressionMultiplicative();

            state._fsp--;

             
                    current = this_ExpressionMultiplicative_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4424:1: ( () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) ) )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( ((LA97_0>=72 && LA97_0<=73)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // InternalCal.g:4424:2: () ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) ) ( (lv_right_3_0= ruleExpressionMultiplicative ) )
            	    {
            	    // InternalCal.g:4424:2: ()
            	    // InternalCal.g:4425:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionAdditiveAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4430:2: ( ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) ) )
            	    // InternalCal.g:4431:1: ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) )
            	    {
            	    // InternalCal.g:4431:1: ( (lv_operator_2_1= '+' | lv_operator_2_2= '-' ) )
            	    // InternalCal.g:4432:1: (lv_operator_2_1= '+' | lv_operator_2_2= '-' )
            	    {
            	    // InternalCal.g:4432:1: (lv_operator_2_1= '+' | lv_operator_2_2= '-' )
            	    int alt96=2;
            	    int LA96_0 = input.LA(1);

            	    if ( (LA96_0==72) ) {
            	        alt96=1;
            	    }
            	    else if ( (LA96_0==73) ) {
            	        alt96=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 96, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt96) {
            	        case 1 :
            	            // InternalCal.g:4433:3: lv_operator_2_1= '+'
            	            {
            	            lv_operator_2_1=(Token)match(input,72,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionAdditiveAccess().getOperatorPlusSignKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionAdditiveRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:4445:8: lv_operator_2_2= '-'
            	            {
            	            lv_operator_2_2=(Token)match(input,73,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionAdditiveAccess().getOperatorHyphenMinusKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionAdditiveRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:4460:2: ( (lv_right_3_0= ruleExpressionMultiplicative ) )
            	    // InternalCal.g:4461:1: (lv_right_3_0= ruleExpressionMultiplicative )
            	    {
            	    // InternalCal.g:4461:1: (lv_right_3_0= ruleExpressionMultiplicative )
            	    // InternalCal.g:4462:3: lv_right_3_0= ruleExpressionMultiplicative
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionAdditiveAccess().getRightExpressionMultiplicativeParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_80);
            	    lv_right_3_0=ruleExpressionMultiplicative();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionAdditiveRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionMultiplicative");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionAdditive"


    // $ANTLR start "entryRuleExpressionMultiplicative"
    // InternalCal.g:4486:1: entryRuleExpressionMultiplicative returns [EObject current=null] : iv_ruleExpressionMultiplicative= ruleExpressionMultiplicative EOF ;
    public final EObject entryRuleExpressionMultiplicative() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionMultiplicative = null;


        try {
            // InternalCal.g:4487:2: (iv_ruleExpressionMultiplicative= ruleExpressionMultiplicative EOF )
            // InternalCal.g:4488:2: iv_ruleExpressionMultiplicative= ruleExpressionMultiplicative EOF
            {
             newCompositeNode(grammarAccess.getExpressionMultiplicativeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionMultiplicative=ruleExpressionMultiplicative();

            state._fsp--;

             current =iv_ruleExpressionMultiplicative; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionMultiplicative"


    // $ANTLR start "ruleExpressionMultiplicative"
    // InternalCal.g:4495:1: ruleExpressionMultiplicative returns [EObject current=null] : (this_ExpressionExp_0= ruleExpressionExp ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )* ) ;
    public final EObject ruleExpressionMultiplicative() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        Token lv_operator_2_3=null;
        Token lv_operator_2_4=null;
        EObject this_ExpressionExp_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4498:28: ( (this_ExpressionExp_0= ruleExpressionExp ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )* ) )
            // InternalCal.g:4499:1: (this_ExpressionExp_0= ruleExpressionExp ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )* )
            {
            // InternalCal.g:4499:1: (this_ExpressionExp_0= ruleExpressionExp ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )* )
            // InternalCal.g:4500:5: this_ExpressionExp_0= ruleExpressionExp ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionMultiplicativeAccess().getExpressionExpParserRuleCall_0()); 
                
            pushFollow(FOLLOW_81);
            this_ExpressionExp_0=ruleExpressionExp();

            state._fsp--;

             
                    current = this_ExpressionExp_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4508:1: ( () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) ) )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==41||(LA99_0>=74 && LA99_0<=76)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // InternalCal.g:4508:2: () ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) ) ( (lv_right_3_0= ruleExpressionExp ) )
            	    {
            	    // InternalCal.g:4508:2: ()
            	    // InternalCal.g:4509:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionMultiplicativeAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4514:2: ( ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) ) )
            	    // InternalCal.g:4515:1: ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) )
            	    {
            	    // InternalCal.g:4515:1: ( (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' ) )
            	    // InternalCal.g:4516:1: (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' )
            	    {
            	    // InternalCal.g:4516:1: (lv_operator_2_1= '*' | lv_operator_2_2= '/' | lv_operator_2_3= 'div' | lv_operator_2_4= 'mod' )
            	    int alt98=4;
            	    switch ( input.LA(1) ) {
            	    case 41:
            	        {
            	        alt98=1;
            	        }
            	        break;
            	    case 74:
            	        {
            	        alt98=2;
            	        }
            	        break;
            	    case 75:
            	        {
            	        alt98=3;
            	        }
            	        break;
            	    case 76:
            	        {
            	        alt98=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 98, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt98) {
            	        case 1 :
            	            // InternalCal.g:4517:3: lv_operator_2_1= '*'
            	            {
            	            lv_operator_2_1=(Token)match(input,41,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_1, grammarAccess.getExpressionMultiplicativeAccess().getOperatorAsteriskKeyword_1_1_0_0());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionMultiplicativeRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
            	            	    

            	            }
            	            break;
            	        case 2 :
            	            // InternalCal.g:4529:8: lv_operator_2_2= '/'
            	            {
            	            lv_operator_2_2=(Token)match(input,74,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_2, grammarAccess.getExpressionMultiplicativeAccess().getOperatorSolidusKeyword_1_1_0_1());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionMultiplicativeRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
            	            	    

            	            }
            	            break;
            	        case 3 :
            	            // InternalCal.g:4541:8: lv_operator_2_3= 'div'
            	            {
            	            lv_operator_2_3=(Token)match(input,75,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_3, grammarAccess.getExpressionMultiplicativeAccess().getOperatorDivKeyword_1_1_0_2());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionMultiplicativeRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_3, null);
            	            	    

            	            }
            	            break;
            	        case 4 :
            	            // InternalCal.g:4553:8: lv_operator_2_4= 'mod'
            	            {
            	            lv_operator_2_4=(Token)match(input,76,FOLLOW_13); 

            	                    newLeafNode(lv_operator_2_4, grammarAccess.getExpressionMultiplicativeAccess().getOperatorModKeyword_1_1_0_3());
            	                

            	            	        if (current==null) {
            	            	            current = createModelElement(grammarAccess.getExpressionMultiplicativeRule());
            	            	        }
            	                   		setWithLastConsumed(current, "operator", lv_operator_2_4, null);
            	            	    

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalCal.g:4568:2: ( (lv_right_3_0= ruleExpressionExp ) )
            	    // InternalCal.g:4569:1: (lv_right_3_0= ruleExpressionExp )
            	    {
            	    // InternalCal.g:4569:1: (lv_right_3_0= ruleExpressionExp )
            	    // InternalCal.g:4570:3: lv_right_3_0= ruleExpressionExp
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionMultiplicativeAccess().getRightExpressionExpParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_81);
            	    lv_right_3_0=ruleExpressionExp();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionMultiplicativeRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionExp");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionMultiplicative"


    // $ANTLR start "entryRuleExpressionExp"
    // InternalCal.g:4594:1: entryRuleExpressionExp returns [EObject current=null] : iv_ruleExpressionExp= ruleExpressionExp EOF ;
    public final EObject entryRuleExpressionExp() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionExp = null;


        try {
            // InternalCal.g:4595:2: (iv_ruleExpressionExp= ruleExpressionExp EOF )
            // InternalCal.g:4596:2: iv_ruleExpressionExp= ruleExpressionExp EOF
            {
             newCompositeNode(grammarAccess.getExpressionExpRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionExp=ruleExpressionExp();

            state._fsp--;

             current =iv_ruleExpressionExp; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionExp"


    // $ANTLR start "ruleExpressionExp"
    // InternalCal.g:4603:1: ruleExpressionExp returns [EObject current=null] : (this_ExpressionUnary_0= ruleExpressionUnary ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )* ) ;
    public final EObject ruleExpressionExp() throws RecognitionException {
        EObject current = null;

        Token lv_operator_2_0=null;
        EObject this_ExpressionUnary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4606:28: ( (this_ExpressionUnary_0= ruleExpressionUnary ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )* ) )
            // InternalCal.g:4607:1: (this_ExpressionUnary_0= ruleExpressionUnary ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )* )
            {
            // InternalCal.g:4607:1: (this_ExpressionUnary_0= ruleExpressionUnary ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )* )
            // InternalCal.g:4608:5: this_ExpressionUnary_0= ruleExpressionUnary ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getExpressionExpAccess().getExpressionUnaryParserRuleCall_0()); 
                
            pushFollow(FOLLOW_82);
            this_ExpressionUnary_0=ruleExpressionUnary();

            state._fsp--;

             
                    current = this_ExpressionUnary_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:4616:1: ( () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) ) )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( (LA100_0==77) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // InternalCal.g:4616:2: () ( (lv_operator_2_0= '**' ) ) ( (lv_right_3_0= ruleExpressionUnary ) )
            	    {
            	    // InternalCal.g:4616:2: ()
            	    // InternalCal.g:4617:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getExpressionExpAccess().getExpressionBinaryLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    // InternalCal.g:4622:2: ( (lv_operator_2_0= '**' ) )
            	    // InternalCal.g:4623:1: (lv_operator_2_0= '**' )
            	    {
            	    // InternalCal.g:4623:1: (lv_operator_2_0= '**' )
            	    // InternalCal.g:4624:3: lv_operator_2_0= '**'
            	    {
            	    lv_operator_2_0=(Token)match(input,77,FOLLOW_13); 

            	            newLeafNode(lv_operator_2_0, grammarAccess.getExpressionExpAccess().getOperatorAsteriskAsteriskKeyword_1_1_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getExpressionExpRule());
            	    	        }
            	           		setWithLastConsumed(current, "operator", lv_operator_2_0, "**");
            	    	    

            	    }


            	    }

            	    // InternalCal.g:4637:2: ( (lv_right_3_0= ruleExpressionUnary ) )
            	    // InternalCal.g:4638:1: (lv_right_3_0= ruleExpressionUnary )
            	    {
            	    // InternalCal.g:4638:1: (lv_right_3_0= ruleExpressionUnary )
            	    // InternalCal.g:4639:3: lv_right_3_0= ruleExpressionUnary
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionExpAccess().getRightExpressionUnaryParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_82);
            	    lv_right_3_0=ruleExpressionUnary();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionExpRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_3_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionUnary");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop100;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionExp"


    // $ANTLR start "entryRuleExpressionUnary"
    // InternalCal.g:4663:1: entryRuleExpressionUnary returns [EObject current=null] : iv_ruleExpressionUnary= ruleExpressionUnary EOF ;
    public final EObject entryRuleExpressionUnary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionUnary = null;


        try {
            // InternalCal.g:4664:2: (iv_ruleExpressionUnary= ruleExpressionUnary EOF )
            // InternalCal.g:4665:2: iv_ruleExpressionUnary= ruleExpressionUnary EOF
            {
             newCompositeNode(grammarAccess.getExpressionUnaryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionUnary=ruleExpressionUnary();

            state._fsp--;

             current =iv_ruleExpressionUnary; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionUnary"


    // $ANTLR start "ruleExpressionUnary"
    // InternalCal.g:4672:1: ruleExpressionUnary returns [EObject current=null] : ( ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) ) | this_ExpressionPostfix_3= ruleExpressionPostfix ) ;
    public final EObject ruleExpressionUnary() throws RecognitionException {
        EObject current = null;

        Token lv_unaryOperator_1_1=null;
        Token lv_unaryOperator_1_2=null;
        Token lv_unaryOperator_1_3=null;
        Token lv_unaryOperator_1_4=null;
        EObject lv_expression_2_0 = null;

        EObject this_ExpressionPostfix_3 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4675:28: ( ( ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) ) | this_ExpressionPostfix_3= ruleExpressionPostfix ) )
            // InternalCal.g:4676:1: ( ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) ) | this_ExpressionPostfix_3= ruleExpressionPostfix )
            {
            // InternalCal.g:4676:1: ( ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) ) | this_ExpressionPostfix_3= ruleExpressionPostfix )
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==73||(LA102_0>=78 && LA102_0<=80)) ) {
                alt102=1;
            }
            else if ( ((LA102_0>=RULE_ID && LA102_0<=RULE_STRING)||LA102_0==25||LA102_0==48||LA102_0==55||(LA102_0>=82 && LA102_0<=83)||LA102_0==94) ) {
                alt102=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }
            switch (alt102) {
                case 1 :
                    // InternalCal.g:4676:2: ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) )
                    {
                    // InternalCal.g:4676:2: ( () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) ) )
                    // InternalCal.g:4676:3: () ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) ) ( (lv_expression_2_0= ruleExpressionPostfix ) )
                    {
                    // InternalCal.g:4676:3: ()
                    // InternalCal.g:4677:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getExpressionUnaryAccess().getExpressionUnaryAction_0_0(),
                                current);
                        

                    }

                    // InternalCal.g:4682:2: ( ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) ) )
                    // InternalCal.g:4683:1: ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) )
                    {
                    // InternalCal.g:4683:1: ( (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' ) )
                    // InternalCal.g:4684:1: (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' )
                    {
                    // InternalCal.g:4684:1: (lv_unaryOperator_1_1= '~' | lv_unaryOperator_1_2= 'not' | lv_unaryOperator_1_3= '-' | lv_unaryOperator_1_4= '#' )
                    int alt101=4;
                    switch ( input.LA(1) ) {
                    case 78:
                        {
                        alt101=1;
                        }
                        break;
                    case 79:
                        {
                        alt101=2;
                        }
                        break;
                    case 73:
                        {
                        alt101=3;
                        }
                        break;
                    case 80:
                        {
                        alt101=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 101, 0, input);

                        throw nvae;
                    }

                    switch (alt101) {
                        case 1 :
                            // InternalCal.g:4685:3: lv_unaryOperator_1_1= '~'
                            {
                            lv_unaryOperator_1_1=(Token)match(input,78,FOLLOW_13); 

                                    newLeafNode(lv_unaryOperator_1_1, grammarAccess.getExpressionUnaryAccess().getUnaryOperatorTildeKeyword_0_1_0_0());
                                

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getExpressionUnaryRule());
                            	        }
                                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_1_1, null);
                            	    

                            }
                            break;
                        case 2 :
                            // InternalCal.g:4697:8: lv_unaryOperator_1_2= 'not'
                            {
                            lv_unaryOperator_1_2=(Token)match(input,79,FOLLOW_13); 

                                    newLeafNode(lv_unaryOperator_1_2, grammarAccess.getExpressionUnaryAccess().getUnaryOperatorNotKeyword_0_1_0_1());
                                

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getExpressionUnaryRule());
                            	        }
                                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_1_2, null);
                            	    

                            }
                            break;
                        case 3 :
                            // InternalCal.g:4709:8: lv_unaryOperator_1_3= '-'
                            {
                            lv_unaryOperator_1_3=(Token)match(input,73,FOLLOW_13); 

                                    newLeafNode(lv_unaryOperator_1_3, grammarAccess.getExpressionUnaryAccess().getUnaryOperatorHyphenMinusKeyword_0_1_0_2());
                                

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getExpressionUnaryRule());
                            	        }
                                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_1_3, null);
                            	    

                            }
                            break;
                        case 4 :
                            // InternalCal.g:4721:8: lv_unaryOperator_1_4= '#'
                            {
                            lv_unaryOperator_1_4=(Token)match(input,80,FOLLOW_13); 

                                    newLeafNode(lv_unaryOperator_1_4, grammarAccess.getExpressionUnaryAccess().getUnaryOperatorNumberSignKeyword_0_1_0_3());
                                

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getExpressionUnaryRule());
                            	        }
                                   		setWithLastConsumed(current, "unaryOperator", lv_unaryOperator_1_4, null);
                            	    

                            }
                            break;

                    }


                    }


                    }

                    // InternalCal.g:4736:2: ( (lv_expression_2_0= ruleExpressionPostfix ) )
                    // InternalCal.g:4737:1: (lv_expression_2_0= ruleExpressionPostfix )
                    {
                    // InternalCal.g:4737:1: (lv_expression_2_0= ruleExpressionPostfix )
                    // InternalCal.g:4738:3: lv_expression_2_0= ruleExpressionPostfix
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionUnaryAccess().getExpressionExpressionPostfixParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_expression_2_0=ruleExpressionPostfix();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionUnaryRule());
                    	        }
                           		set(
                           			current, 
                           			"expression",
                            		lv_expression_2_0, 
                            		"net.sf.orcc.cal.Cal.ExpressionPostfix");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalCal.g:4756:5: this_ExpressionPostfix_3= ruleExpressionPostfix
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionUnaryAccess().getExpressionPostfixParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionPostfix_3=ruleExpressionPostfix();

                    state._fsp--;

                     
                            current = this_ExpressionPostfix_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionUnary"


    // $ANTLR start "entryRuleExpressionPostfix"
    // InternalCal.g:4772:1: entryRuleExpressionPostfix returns [EObject current=null] : iv_ruleExpressionPostfix= ruleExpressionPostfix EOF ;
    public final EObject entryRuleExpressionPostfix() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionPostfix = null;


        try {
            // InternalCal.g:4773:2: (iv_ruleExpressionPostfix= ruleExpressionPostfix EOF )
            // InternalCal.g:4774:2: iv_ruleExpressionPostfix= ruleExpressionPostfix EOF
            {
             newCompositeNode(grammarAccess.getExpressionPostfixRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionPostfix=ruleExpressionPostfix();

            state._fsp--;

             current =iv_ruleExpressionPostfix; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionPostfix"


    // $ANTLR start "ruleExpressionPostfix"
    // InternalCal.g:4781:1: ruleExpressionPostfix returns [EObject current=null] : (this_ExpressionList_0= ruleExpressionList | this_ExpressionCall_1= ruleExpressionCall | this_ExpressionIndex_2= ruleExpressionIndex | this_ExpressionIf_3= ruleExpressionIf | this_ExpressionLiteral_4= ruleExpressionLiteral | this_ExpressionVariable_5= ruleExpressionVariable | (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' ) ) ;
    public final EObject ruleExpressionPostfix() throws RecognitionException {
        EObject current = null;

        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject this_ExpressionList_0 = null;

        EObject this_ExpressionCall_1 = null;

        EObject this_ExpressionIndex_2 = null;

        EObject this_ExpressionIf_3 = null;

        EObject this_ExpressionLiteral_4 = null;

        EObject this_ExpressionVariable_5 = null;

        EObject this_AstExpression_7 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4784:28: ( (this_ExpressionList_0= ruleExpressionList | this_ExpressionCall_1= ruleExpressionCall | this_ExpressionIndex_2= ruleExpressionIndex | this_ExpressionIf_3= ruleExpressionIf | this_ExpressionLiteral_4= ruleExpressionLiteral | this_ExpressionVariable_5= ruleExpressionVariable | (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' ) ) )
            // InternalCal.g:4785:1: (this_ExpressionList_0= ruleExpressionList | this_ExpressionCall_1= ruleExpressionCall | this_ExpressionIndex_2= ruleExpressionIndex | this_ExpressionIf_3= ruleExpressionIf | this_ExpressionLiteral_4= ruleExpressionLiteral | this_ExpressionVariable_5= ruleExpressionVariable | (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' ) )
            {
            // InternalCal.g:4785:1: (this_ExpressionList_0= ruleExpressionList | this_ExpressionCall_1= ruleExpressionCall | this_ExpressionIndex_2= ruleExpressionIndex | this_ExpressionIf_3= ruleExpressionIf | this_ExpressionLiteral_4= ruleExpressionLiteral | this_ExpressionVariable_5= ruleExpressionVariable | (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' ) )
            int alt103=7;
            alt103 = dfa103.predict(input);
            switch (alt103) {
                case 1 :
                    // InternalCal.g:4786:5: this_ExpressionList_0= ruleExpressionList
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionListParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionList_0=ruleExpressionList();

                    state._fsp--;

                     
                            current = this_ExpressionList_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:4796:5: this_ExpressionCall_1= ruleExpressionCall
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionCallParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionCall_1=ruleExpressionCall();

                    state._fsp--;

                     
                            current = this_ExpressionCall_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // InternalCal.g:4806:5: this_ExpressionIndex_2= ruleExpressionIndex
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionIndexParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionIndex_2=ruleExpressionIndex();

                    state._fsp--;

                     
                            current = this_ExpressionIndex_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // InternalCal.g:4816:5: this_ExpressionIf_3= ruleExpressionIf
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionIfParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionIf_3=ruleExpressionIf();

                    state._fsp--;

                     
                            current = this_ExpressionIf_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // InternalCal.g:4826:5: this_ExpressionLiteral_4= ruleExpressionLiteral
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionLiteralParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionLiteral_4=ruleExpressionLiteral();

                    state._fsp--;

                     
                            current = this_ExpressionLiteral_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // InternalCal.g:4836:5: this_ExpressionVariable_5= ruleExpressionVariable
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getExpressionVariableParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionVariable_5=ruleExpressionVariable();

                    state._fsp--;

                     
                            current = this_ExpressionVariable_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // InternalCal.g:4845:6: (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' )
                    {
                    // InternalCal.g:4845:6: (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' )
                    // InternalCal.g:4845:8: otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,25,FOLLOW_13); 

                        	newLeafNode(otherlv_6, grammarAccess.getExpressionPostfixAccess().getLeftParenthesisKeyword_6_0());
                        
                     
                            newCompositeNode(grammarAccess.getExpressionPostfixAccess().getAstExpressionParserRuleCall_6_1()); 
                        
                    pushFollow(FOLLOW_37);
                    this_AstExpression_7=ruleAstExpression();

                    state._fsp--;

                     
                            current = this_AstExpression_7; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_8=(Token)match(input,27,FOLLOW_2); 

                        	newLeafNode(otherlv_8, grammarAccess.getExpressionPostfixAccess().getRightParenthesisKeyword_6_2());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionPostfix"


    // $ANTLR start "entryRuleExpressionCall"
    // InternalCal.g:4870:1: entryRuleExpressionCall returns [EObject current=null] : iv_ruleExpressionCall= ruleExpressionCall EOF ;
    public final EObject entryRuleExpressionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionCall = null;


        try {
            // InternalCal.g:4871:2: (iv_ruleExpressionCall= ruleExpressionCall EOF )
            // InternalCal.g:4872:2: iv_ruleExpressionCall= ruleExpressionCall EOF
            {
             newCompositeNode(grammarAccess.getExpressionCallRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionCall=ruleExpressionCall();

            state._fsp--;

             current =iv_ruleExpressionCall; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionCall"


    // $ANTLR start "ruleExpressionCall"
    // InternalCal.g:4879:1: ruleExpressionCall returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' ) ;
    public final EObject ruleExpressionCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4882:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' ) )
            // InternalCal.g:4883:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' )
            {
            // InternalCal.g:4883:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')' )
            // InternalCal.g:4883:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( ( ruleQualifiedName ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )? otherlv_6= ')'
            {
            // InternalCal.g:4883:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==94) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // InternalCal.g:4884:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:4884:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:4885:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionCallAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_59);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionCallRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop104;
                }
            } while (true);

            // InternalCal.g:4901:3: ( ( ruleQualifiedName ) )
            // InternalCal.g:4902:1: ( ruleQualifiedName )
            {
            // InternalCal.g:4902:1: ( ruleQualifiedName )
            // InternalCal.g:4903:3: ruleQualifiedName
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getExpressionCallRule());
            	        }
                    
             
            	        newCompositeNode(grammarAccess.getExpressionCallAccess().getFunctionFunctionCrossReference_1_0()); 
            	    
            pushFollow(FOLLOW_7);
            ruleQualifiedName();

            state._fsp--;

             
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,25,FOLLOW_62); 

                	newLeafNode(otherlv_2, grammarAccess.getExpressionCallAccess().getLeftParenthesisKeyword_2());
                
            // InternalCal.g:4920:1: ( ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )* )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( ((LA106_0>=RULE_ID && LA106_0<=RULE_STRING)||LA106_0==25||LA106_0==48||LA106_0==55||LA106_0==73||(LA106_0>=78 && LA106_0<=80)||(LA106_0>=82 && LA106_0<=83)||LA106_0==94) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // InternalCal.g:4920:2: ( (lv_parameters_3_0= ruleAstExpression ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )*
                    {
                    // InternalCal.g:4920:2: ( (lv_parameters_3_0= ruleAstExpression ) )
                    // InternalCal.g:4921:1: (lv_parameters_3_0= ruleAstExpression )
                    {
                    // InternalCal.g:4921:1: (lv_parameters_3_0= ruleAstExpression )
                    // InternalCal.g:4922:3: lv_parameters_3_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionCallAccess().getParametersAstExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_parameters_3_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionCallRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_3_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:4938:2: (otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) ) )*
                    loop105:
                    do {
                        int alt105=2;
                        int LA105_0 = input.LA(1);

                        if ( (LA105_0==26) ) {
                            alt105=1;
                        }


                        switch (alt105) {
                    	case 1 :
                    	    // InternalCal.g:4938:4: otherlv_4= ',' ( (lv_parameters_5_0= ruleAstExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,26,FOLLOW_13); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getExpressionCallAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // InternalCal.g:4942:1: ( (lv_parameters_5_0= ruleAstExpression ) )
                    	    // InternalCal.g:4943:1: (lv_parameters_5_0= ruleAstExpression )
                    	    {
                    	    // InternalCal.g:4943:1: (lv_parameters_5_0= ruleAstExpression )
                    	    // InternalCal.g:4944:3: lv_parameters_5_0= ruleAstExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getExpressionCallAccess().getParametersAstExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_parameters_5_0=ruleAstExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getExpressionCallRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_5_0, 
                    	            		"net.sf.orcc.cal.Cal.AstExpression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop105;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,27,FOLLOW_2); 

                	newLeafNode(otherlv_6, grammarAccess.getExpressionCallAccess().getRightParenthesisKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionCall"


    // $ANTLR start "entryRuleExpressionIndex"
    // InternalCal.g:4972:1: entryRuleExpressionIndex returns [EObject current=null] : iv_ruleExpressionIndex= ruleExpressionIndex EOF ;
    public final EObject entryRuleExpressionIndex() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionIndex = null;


        try {
            // InternalCal.g:4973:2: (iv_ruleExpressionIndex= ruleExpressionIndex EOF )
            // InternalCal.g:4974:2: iv_ruleExpressionIndex= ruleExpressionIndex EOF
            {
             newCompositeNode(grammarAccess.getExpressionIndexRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionIndex=ruleExpressionIndex();

            state._fsp--;

             current =iv_ruleExpressionIndex; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionIndex"


    // $ANTLR start "ruleExpressionIndex"
    // InternalCal.g:4981:1: ruleExpressionIndex returns [EObject current=null] : ( ( (lv_source_0_0= ruleVariableReference ) ) (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+ ) ;
    public final EObject ruleExpressionIndex() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_source_0_0 = null;

        EObject lv_indexes_2_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:4984:28: ( ( ( (lv_source_0_0= ruleVariableReference ) ) (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+ ) )
            // InternalCal.g:4985:1: ( ( (lv_source_0_0= ruleVariableReference ) ) (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+ )
            {
            // InternalCal.g:4985:1: ( ( (lv_source_0_0= ruleVariableReference ) ) (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+ )
            // InternalCal.g:4985:2: ( (lv_source_0_0= ruleVariableReference ) ) (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+
            {
            // InternalCal.g:4985:2: ( (lv_source_0_0= ruleVariableReference ) )
            // InternalCal.g:4986:1: (lv_source_0_0= ruleVariableReference )
            {
            // InternalCal.g:4986:1: (lv_source_0_0= ruleVariableReference )
            // InternalCal.g:4987:3: lv_source_0_0= ruleVariableReference
            {
             
            	        newCompositeNode(grammarAccess.getExpressionIndexAccess().getSourceVariableReferenceParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_55);
            lv_source_0_0=ruleVariableReference();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionIndexRule());
            	        }
                   		set(
                   			current, 
                   			"source",
                    		lv_source_0_0, 
                    		"net.sf.orcc.cal.Cal.VariableReference");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:5003:2: (otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']' )+
            int cnt107=0;
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==48) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // InternalCal.g:5003:4: otherlv_1= '[' ( (lv_indexes_2_0= ruleAstExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,48,FOLLOW_13); 

            	        	newLeafNode(otherlv_1, grammarAccess.getExpressionIndexAccess().getLeftSquareBracketKeyword_1_0());
            	        
            	    // InternalCal.g:5007:1: ( (lv_indexes_2_0= ruleAstExpression ) )
            	    // InternalCal.g:5008:1: (lv_indexes_2_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:5008:1: (lv_indexes_2_0= ruleAstExpression )
            	    // InternalCal.g:5009:3: lv_indexes_2_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionIndexAccess().getIndexesAstExpressionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_61);
            	    lv_indexes_2_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionIndexRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"indexes",
            	            		lv_indexes_2_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }

            	    otherlv_3=(Token)match(input,49,FOLLOW_83); 

            	        	newLeafNode(otherlv_3, grammarAccess.getExpressionIndexAccess().getRightSquareBracketKeyword_1_2());
            	        

            	    }
            	    break;

            	default :
            	    if ( cnt107 >= 1 ) break loop107;
                        EarlyExitException eee =
                            new EarlyExitException(107, input);
                        throw eee;
                }
                cnt107++;
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionIndex"


    // $ANTLR start "entryRuleExpressionIf"
    // InternalCal.g:5037:1: entryRuleExpressionIf returns [EObject current=null] : iv_ruleExpressionIf= ruleExpressionIf EOF ;
    public final EObject entryRuleExpressionIf() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionIf = null;


        try {
            // InternalCal.g:5038:2: (iv_ruleExpressionIf= ruleExpressionIf EOF )
            // InternalCal.g:5039:2: iv_ruleExpressionIf= ruleExpressionIf EOF
            {
             newCompositeNode(grammarAccess.getExpressionIfRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionIf=ruleExpressionIf();

            state._fsp--;

             current =iv_ruleExpressionIf; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionIf"


    // $ANTLR start "ruleExpressionIf"
    // InternalCal.g:5046:1: ruleExpressionIf returns [EObject current=null] : (otherlv_0= 'if' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ( (lv_elsifs_4_0= ruleExpressionElsif ) )* otherlv_5= 'else' ( (lv_else_6_0= ruleAstExpression ) ) otherlv_7= 'end' ) ;
    public final EObject ruleExpressionIf() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_condition_1_0 = null;

        EObject lv_then_3_0 = null;

        EObject lv_elsifs_4_0 = null;

        EObject lv_else_6_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5049:28: ( (otherlv_0= 'if' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ( (lv_elsifs_4_0= ruleExpressionElsif ) )* otherlv_5= 'else' ( (lv_else_6_0= ruleAstExpression ) ) otherlv_7= 'end' ) )
            // InternalCal.g:5050:1: (otherlv_0= 'if' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ( (lv_elsifs_4_0= ruleExpressionElsif ) )* otherlv_5= 'else' ( (lv_else_6_0= ruleAstExpression ) ) otherlv_7= 'end' )
            {
            // InternalCal.g:5050:1: (otherlv_0= 'if' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ( (lv_elsifs_4_0= ruleExpressionElsif ) )* otherlv_5= 'else' ( (lv_else_6_0= ruleAstExpression ) ) otherlv_7= 'end' )
            // InternalCal.g:5050:3: otherlv_0= 'if' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ( (lv_elsifs_4_0= ruleExpressionElsif ) )* otherlv_5= 'else' ( (lv_else_6_0= ruleAstExpression ) ) otherlv_7= 'end'
            {
            otherlv_0=(Token)match(input,55,FOLLOW_13); 

                	newLeafNode(otherlv_0, grammarAccess.getExpressionIfAccess().getIfKeyword_0());
                
            // InternalCal.g:5054:1: ( (lv_condition_1_0= ruleAstExpression ) )
            // InternalCal.g:5055:1: (lv_condition_1_0= ruleAstExpression )
            {
            // InternalCal.g:5055:1: (lv_condition_1_0= ruleAstExpression )
            // InternalCal.g:5056:3: lv_condition_1_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionIfAccess().getConditionAstExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_68);
            lv_condition_1_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionIfRule());
            	        }
                   		set(
                   			current, 
                   			"condition",
                    		lv_condition_1_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,56,FOLLOW_13); 

                	newLeafNode(otherlv_2, grammarAccess.getExpressionIfAccess().getThenKeyword_2());
                
            // InternalCal.g:5076:1: ( (lv_then_3_0= ruleAstExpression ) )
            // InternalCal.g:5077:1: (lv_then_3_0= ruleAstExpression )
            {
            // InternalCal.g:5077:1: (lv_then_3_0= ruleAstExpression )
            // InternalCal.g:5078:3: lv_then_3_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionIfAccess().getThenAstExpressionParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_84);
            lv_then_3_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionIfRule());
            	        }
                   		set(
                   			current, 
                   			"then",
                    		lv_then_3_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:5094:2: ( (lv_elsifs_4_0= ruleExpressionElsif ) )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( (LA108_0==58) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // InternalCal.g:5095:1: (lv_elsifs_4_0= ruleExpressionElsif )
            	    {
            	    // InternalCal.g:5095:1: (lv_elsifs_4_0= ruleExpressionElsif )
            	    // InternalCal.g:5096:3: lv_elsifs_4_0= ruleExpressionElsif
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionIfAccess().getElsifsExpressionElsifParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_84);
            	    lv_elsifs_4_0=ruleExpressionElsif();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionIfRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"elsifs",
            	            		lv_elsifs_4_0, 
            	            		"net.sf.orcc.cal.Cal.ExpressionElsif");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop108;
                }
            } while (true);

            otherlv_5=(Token)match(input,57,FOLLOW_13); 

                	newLeafNode(otherlv_5, grammarAccess.getExpressionIfAccess().getElseKeyword_5());
                
            // InternalCal.g:5116:1: ( (lv_else_6_0= ruleAstExpression ) )
            // InternalCal.g:5117:1: (lv_else_6_0= ruleAstExpression )
            {
            // InternalCal.g:5117:1: (lv_else_6_0= ruleAstExpression )
            // InternalCal.g:5118:3: lv_else_6_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionIfAccess().getElseAstExpressionParserRuleCall_6_0()); 
            	    
            pushFollow(FOLLOW_26);
            lv_else_6_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionIfRule());
            	        }
                   		set(
                   			current, 
                   			"else",
                    		lv_else_6_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_7=(Token)match(input,23,FOLLOW_2); 

                	newLeafNode(otherlv_7, grammarAccess.getExpressionIfAccess().getEndKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionIf"


    // $ANTLR start "entryRuleExpressionElsif"
    // InternalCal.g:5146:1: entryRuleExpressionElsif returns [EObject current=null] : iv_ruleExpressionElsif= ruleExpressionElsif EOF ;
    public final EObject entryRuleExpressionElsif() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionElsif = null;


        try {
            // InternalCal.g:5147:2: (iv_ruleExpressionElsif= ruleExpressionElsif EOF )
            // InternalCal.g:5148:2: iv_ruleExpressionElsif= ruleExpressionElsif EOF
            {
             newCompositeNode(grammarAccess.getExpressionElsifRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionElsif=ruleExpressionElsif();

            state._fsp--;

             current =iv_ruleExpressionElsif; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionElsif"


    // $ANTLR start "ruleExpressionElsif"
    // InternalCal.g:5155:1: ruleExpressionElsif returns [EObject current=null] : (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ) ;
    public final EObject ruleExpressionElsif() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_then_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5158:28: ( (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) ) )
            // InternalCal.g:5159:1: (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) )
            {
            // InternalCal.g:5159:1: (otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) ) )
            // InternalCal.g:5159:3: otherlv_0= 'elsif' ( (lv_condition_1_0= ruleAstExpression ) ) otherlv_2= 'then' ( (lv_then_3_0= ruleAstExpression ) )
            {
            otherlv_0=(Token)match(input,58,FOLLOW_13); 

                	newLeafNode(otherlv_0, grammarAccess.getExpressionElsifAccess().getElsifKeyword_0());
                
            // InternalCal.g:5163:1: ( (lv_condition_1_0= ruleAstExpression ) )
            // InternalCal.g:5164:1: (lv_condition_1_0= ruleAstExpression )
            {
            // InternalCal.g:5164:1: (lv_condition_1_0= ruleAstExpression )
            // InternalCal.g:5165:3: lv_condition_1_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionElsifAccess().getConditionAstExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_68);
            lv_condition_1_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionElsifRule());
            	        }
                   		set(
                   			current, 
                   			"condition",
                    		lv_condition_1_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,56,FOLLOW_13); 

                	newLeafNode(otherlv_2, grammarAccess.getExpressionElsifAccess().getThenKeyword_2());
                
            // InternalCal.g:5185:1: ( (lv_then_3_0= ruleAstExpression ) )
            // InternalCal.g:5186:1: (lv_then_3_0= ruleAstExpression )
            {
            // InternalCal.g:5186:1: (lv_then_3_0= ruleAstExpression )
            // InternalCal.g:5187:3: lv_then_3_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionElsifAccess().getThenAstExpressionParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_2);
            lv_then_3_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionElsifRule());
            	        }
                   		set(
                   			current, 
                   			"then",
                    		lv_then_3_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionElsif"


    // $ANTLR start "entryRuleExpressionList"
    // InternalCal.g:5211:1: entryRuleExpressionList returns [EObject current=null] : iv_ruleExpressionList= ruleExpressionList EOF ;
    public final EObject entryRuleExpressionList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionList = null;


        try {
            // InternalCal.g:5212:2: (iv_ruleExpressionList= ruleExpressionList EOF )
            // InternalCal.g:5213:2: iv_ruleExpressionList= ruleExpressionList EOF
            {
             newCompositeNode(grammarAccess.getExpressionListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionList=ruleExpressionList();

            state._fsp--;

             current =iv_ruleExpressionList; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionList"


    // $ANTLR start "ruleExpressionList"
    // InternalCal.g:5220:1: ruleExpressionList returns [EObject current=null] : (otherlv_0= '[' ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )? otherlv_8= ']' ) ;
    public final EObject ruleExpressionList() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_expressions_1_0 = null;

        EObject lv_expressions_3_0 = null;

        EObject lv_generators_5_0 = null;

        EObject lv_generators_7_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5223:28: ( (otherlv_0= '[' ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )? otherlv_8= ']' ) )
            // InternalCal.g:5224:1: (otherlv_0= '[' ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )? otherlv_8= ']' )
            {
            // InternalCal.g:5224:1: (otherlv_0= '[' ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )? otherlv_8= ']' )
            // InternalCal.g:5224:3: otherlv_0= '[' ( (lv_expressions_1_0= ruleAstExpression ) ) (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )* (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )? otherlv_8= ']'
            {
            otherlv_0=(Token)match(input,48,FOLLOW_13); 

                	newLeafNode(otherlv_0, grammarAccess.getExpressionListAccess().getLeftSquareBracketKeyword_0());
                
            // InternalCal.g:5228:1: ( (lv_expressions_1_0= ruleAstExpression ) )
            // InternalCal.g:5229:1: (lv_expressions_1_0= ruleAstExpression )
            {
            // InternalCal.g:5229:1: (lv_expressions_1_0= ruleAstExpression )
            // InternalCal.g:5230:3: lv_expressions_1_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getExpressionListAccess().getExpressionsAstExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_85);
            lv_expressions_1_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionListRule());
            	        }
                   		add(
                   			current, 
                   			"expressions",
                    		lv_expressions_1_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:5246:2: (otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) ) )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==26) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // InternalCal.g:5246:4: otherlv_2= ',' ( (lv_expressions_3_0= ruleAstExpression ) )
            	    {
            	    otherlv_2=(Token)match(input,26,FOLLOW_13); 

            	        	newLeafNode(otherlv_2, grammarAccess.getExpressionListAccess().getCommaKeyword_2_0());
            	        
            	    // InternalCal.g:5250:1: ( (lv_expressions_3_0= ruleAstExpression ) )
            	    // InternalCal.g:5251:1: (lv_expressions_3_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:5251:1: (lv_expressions_3_0= ruleAstExpression )
            	    // InternalCal.g:5252:3: lv_expressions_3_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionListAccess().getExpressionsAstExpressionParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_85);
            	    lv_expressions_3_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionListRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"expressions",
            	            		lv_expressions_3_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop109;
                }
            } while (true);

            // InternalCal.g:5268:4: (otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )* )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==22) ) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // InternalCal.g:5268:6: otherlv_4= ':' ( (lv_generators_5_0= ruleGenerator ) ) (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )*
                    {
                    otherlv_4=(Token)match(input,22,FOLLOW_86); 

                        	newLeafNode(otherlv_4, grammarAccess.getExpressionListAccess().getColonKeyword_3_0());
                        
                    // InternalCal.g:5272:1: ( (lv_generators_5_0= ruleGenerator ) )
                    // InternalCal.g:5273:1: (lv_generators_5_0= ruleGenerator )
                    {
                    // InternalCal.g:5273:1: (lv_generators_5_0= ruleGenerator )
                    // InternalCal.g:5274:3: lv_generators_5_0= ruleGenerator
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionListAccess().getGeneratorsGeneratorParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_56);
                    lv_generators_5_0=ruleGenerator();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	        }
                           		add(
                           			current, 
                           			"generators",
                            		lv_generators_5_0, 
                            		"net.sf.orcc.cal.Cal.Generator");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:5290:2: (otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) ) )*
                    loop110:
                    do {
                        int alt110=2;
                        int LA110_0 = input.LA(1);

                        if ( (LA110_0==26) ) {
                            alt110=1;
                        }


                        switch (alt110) {
                    	case 1 :
                    	    // InternalCal.g:5290:4: otherlv_6= ',' ( (lv_generators_7_0= ruleGenerator ) )
                    	    {
                    	    otherlv_6=(Token)match(input,26,FOLLOW_86); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getExpressionListAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // InternalCal.g:5294:1: ( (lv_generators_7_0= ruleGenerator ) )
                    	    // InternalCal.g:5295:1: (lv_generators_7_0= ruleGenerator )
                    	    {
                    	    // InternalCal.g:5295:1: (lv_generators_7_0= ruleGenerator )
                    	    // InternalCal.g:5296:3: lv_generators_7_0= ruleGenerator
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getExpressionListAccess().getGeneratorsGeneratorParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_56);
                    	    lv_generators_7_0=ruleGenerator();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"generators",
                    	            		lv_generators_7_0, 
                    	            		"net.sf.orcc.cal.Cal.Generator");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop110;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_8=(Token)match(input,49,FOLLOW_2); 

                	newLeafNode(otherlv_8, grammarAccess.getExpressionListAccess().getRightSquareBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionList"


    // $ANTLR start "entryRuleGenerator"
    // InternalCal.g:5324:1: entryRuleGenerator returns [EObject current=null] : iv_ruleGenerator= ruleGenerator EOF ;
    public final EObject entryRuleGenerator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGenerator = null;


        try {
            // InternalCal.g:5325:2: (iv_ruleGenerator= ruleGenerator EOF )
            // InternalCal.g:5326:2: iv_ruleGenerator= ruleGenerator EOF
            {
             newCompositeNode(grammarAccess.getGeneratorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGenerator=ruleGenerator();

            state._fsp--;

             current =iv_ruleGenerator; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGenerator"


    // $ANTLR start "ruleGenerator"
    // InternalCal.g:5333:1: ruleGenerator returns [EObject current=null] : (otherlv_0= 'for' ( (lv_variable_1_0= ruleVariableDeclaration ) ) otherlv_2= 'in' ( (lv_lower_3_0= ruleAstExpression ) ) otherlv_4= '..' ( (lv_higher_5_0= ruleAstExpression ) ) ) ;
    public final EObject ruleGenerator() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_variable_1_0 = null;

        EObject lv_lower_3_0 = null;

        EObject lv_higher_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5336:28: ( (otherlv_0= 'for' ( (lv_variable_1_0= ruleVariableDeclaration ) ) otherlv_2= 'in' ( (lv_lower_3_0= ruleAstExpression ) ) otherlv_4= '..' ( (lv_higher_5_0= ruleAstExpression ) ) ) )
            // InternalCal.g:5337:1: (otherlv_0= 'for' ( (lv_variable_1_0= ruleVariableDeclaration ) ) otherlv_2= 'in' ( (lv_lower_3_0= ruleAstExpression ) ) otherlv_4= '..' ( (lv_higher_5_0= ruleAstExpression ) ) )
            {
            // InternalCal.g:5337:1: (otherlv_0= 'for' ( (lv_variable_1_0= ruleVariableDeclaration ) ) otherlv_2= 'in' ( (lv_lower_3_0= ruleAstExpression ) ) otherlv_4= '..' ( (lv_higher_5_0= ruleAstExpression ) ) )
            // InternalCal.g:5337:3: otherlv_0= 'for' ( (lv_variable_1_0= ruleVariableDeclaration ) ) otherlv_2= 'in' ( (lv_lower_3_0= ruleAstExpression ) ) otherlv_4= '..' ( (lv_higher_5_0= ruleAstExpression ) )
            {
            otherlv_0=(Token)match(input,81,FOLLOW_16); 

                	newLeafNode(otherlv_0, grammarAccess.getGeneratorAccess().getForKeyword_0());
                
            // InternalCal.g:5341:1: ( (lv_variable_1_0= ruleVariableDeclaration ) )
            // InternalCal.g:5342:1: (lv_variable_1_0= ruleVariableDeclaration )
            {
            // InternalCal.g:5342:1: (lv_variable_1_0= ruleVariableDeclaration )
            // InternalCal.g:5343:3: lv_variable_1_0= ruleVariableDeclaration
            {
             
            	        newCompositeNode(grammarAccess.getGeneratorAccess().getVariableVariableDeclarationParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_64);
            lv_variable_1_0=ruleVariableDeclaration();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGeneratorRule());
            	        }
                   		set(
                   			current, 
                   			"variable",
                    		lv_variable_1_0, 
                    		"net.sf.orcc.cal.Cal.VariableDeclaration");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,53,FOLLOW_13); 

                	newLeafNode(otherlv_2, grammarAccess.getGeneratorAccess().getInKeyword_2());
                
            // InternalCal.g:5363:1: ( (lv_lower_3_0= ruleAstExpression ) )
            // InternalCal.g:5364:1: (lv_lower_3_0= ruleAstExpression )
            {
            // InternalCal.g:5364:1: (lv_lower_3_0= ruleAstExpression )
            // InternalCal.g:5365:3: lv_lower_3_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getGeneratorAccess().getLowerAstExpressionParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_65);
            lv_lower_3_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGeneratorRule());
            	        }
                   		set(
                   			current, 
                   			"lower",
                    		lv_lower_3_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,54,FOLLOW_13); 

                	newLeafNode(otherlv_4, grammarAccess.getGeneratorAccess().getFullStopFullStopKeyword_4());
                
            // InternalCal.g:5385:1: ( (lv_higher_5_0= ruleAstExpression ) )
            // InternalCal.g:5386:1: (lv_higher_5_0= ruleAstExpression )
            {
            // InternalCal.g:5386:1: (lv_higher_5_0= ruleAstExpression )
            // InternalCal.g:5387:3: lv_higher_5_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getGeneratorAccess().getHigherAstExpressionParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_2);
            lv_higher_5_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGeneratorRule());
            	        }
                   		set(
                   			current, 
                   			"higher",
                    		lv_higher_5_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGenerator"


    // $ANTLR start "entryRuleExpressionVariable"
    // InternalCal.g:5411:1: entryRuleExpressionVariable returns [EObject current=null] : iv_ruleExpressionVariable= ruleExpressionVariable EOF ;
    public final EObject entryRuleExpressionVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionVariable = null;


        try {
            // InternalCal.g:5412:2: (iv_ruleExpressionVariable= ruleExpressionVariable EOF )
            // InternalCal.g:5413:2: iv_ruleExpressionVariable= ruleExpressionVariable EOF
            {
             newCompositeNode(grammarAccess.getExpressionVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionVariable=ruleExpressionVariable();

            state._fsp--;

             current =iv_ruleExpressionVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionVariable"


    // $ANTLR start "ruleExpressionVariable"
    // InternalCal.g:5420:1: ruleExpressionVariable returns [EObject current=null] : ( (lv_value_0_0= ruleVariableReference ) ) ;
    public final EObject ruleExpressionVariable() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5423:28: ( ( (lv_value_0_0= ruleVariableReference ) ) )
            // InternalCal.g:5424:1: ( (lv_value_0_0= ruleVariableReference ) )
            {
            // InternalCal.g:5424:1: ( (lv_value_0_0= ruleVariableReference ) )
            // InternalCal.g:5425:1: (lv_value_0_0= ruleVariableReference )
            {
            // InternalCal.g:5425:1: (lv_value_0_0= ruleVariableReference )
            // InternalCal.g:5426:3: lv_value_0_0= ruleVariableReference
            {
             
            	        newCompositeNode(grammarAccess.getExpressionVariableAccess().getValueVariableReferenceParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleVariableReference();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionVariableRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"net.sf.orcc.cal.Cal.VariableReference");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionVariable"


    // $ANTLR start "entryRuleExpressionLiteral"
    // InternalCal.g:5450:1: entryRuleExpressionLiteral returns [EObject current=null] : iv_ruleExpressionLiteral= ruleExpressionLiteral EOF ;
    public final EObject entryRuleExpressionLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionLiteral = null;


        try {
            // InternalCal.g:5451:2: (iv_ruleExpressionLiteral= ruleExpressionLiteral EOF )
            // InternalCal.g:5452:2: iv_ruleExpressionLiteral= ruleExpressionLiteral EOF
            {
             newCompositeNode(grammarAccess.getExpressionLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionLiteral=ruleExpressionLiteral();

            state._fsp--;

             current =iv_ruleExpressionLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionLiteral"


    // $ANTLR start "ruleExpressionLiteral"
    // InternalCal.g:5459:1: ruleExpressionLiteral returns [EObject current=null] : (this_ExpressionBoolean_0= ruleExpressionBoolean | this_ExpressionFloat_1= ruleExpressionFloat | this_ExpressionInteger_2= ruleExpressionInteger | this_ExpressionString_3= ruleExpressionString ) ;
    public final EObject ruleExpressionLiteral() throws RecognitionException {
        EObject current = null;

        EObject this_ExpressionBoolean_0 = null;

        EObject this_ExpressionFloat_1 = null;

        EObject this_ExpressionInteger_2 = null;

        EObject this_ExpressionString_3 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5462:28: ( (this_ExpressionBoolean_0= ruleExpressionBoolean | this_ExpressionFloat_1= ruleExpressionFloat | this_ExpressionInteger_2= ruleExpressionInteger | this_ExpressionString_3= ruleExpressionString ) )
            // InternalCal.g:5463:1: (this_ExpressionBoolean_0= ruleExpressionBoolean | this_ExpressionFloat_1= ruleExpressionFloat | this_ExpressionInteger_2= ruleExpressionInteger | this_ExpressionString_3= ruleExpressionString )
            {
            // InternalCal.g:5463:1: (this_ExpressionBoolean_0= ruleExpressionBoolean | this_ExpressionFloat_1= ruleExpressionFloat | this_ExpressionInteger_2= ruleExpressionInteger | this_ExpressionString_3= ruleExpressionString )
            int alt112=4;
            switch ( input.LA(1) ) {
            case 82:
            case 83:
                {
                alt112=1;
                }
                break;
            case RULE_REAL:
                {
                alt112=2;
                }
                break;
            case RULE_DECIMAL:
            case RULE_OCTAL:
            case RULE_HEX:
                {
                alt112=3;
                }
                break;
            case RULE_STRING:
                {
                alt112=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }

            switch (alt112) {
                case 1 :
                    // InternalCal.g:5464:5: this_ExpressionBoolean_0= ruleExpressionBoolean
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionLiteralAccess().getExpressionBooleanParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionBoolean_0=ruleExpressionBoolean();

                    state._fsp--;

                     
                            current = this_ExpressionBoolean_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:5474:5: this_ExpressionFloat_1= ruleExpressionFloat
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionLiteralAccess().getExpressionFloatParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionFloat_1=ruleExpressionFloat();

                    state._fsp--;

                     
                            current = this_ExpressionFloat_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // InternalCal.g:5484:5: this_ExpressionInteger_2= ruleExpressionInteger
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionLiteralAccess().getExpressionIntegerParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionInteger_2=ruleExpressionInteger();

                    state._fsp--;

                     
                            current = this_ExpressionInteger_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // InternalCal.g:5494:5: this_ExpressionString_3= ruleExpressionString
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionLiteralAccess().getExpressionStringParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_2);
                    this_ExpressionString_3=ruleExpressionString();

                    state._fsp--;

                     
                            current = this_ExpressionString_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionLiteral"


    // $ANTLR start "entryRuleExpressionBoolean"
    // InternalCal.g:5510:1: entryRuleExpressionBoolean returns [EObject current=null] : iv_ruleExpressionBoolean= ruleExpressionBoolean EOF ;
    public final EObject entryRuleExpressionBoolean() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionBoolean = null;


        try {
            // InternalCal.g:5511:2: (iv_ruleExpressionBoolean= ruleExpressionBoolean EOF )
            // InternalCal.g:5512:2: iv_ruleExpressionBoolean= ruleExpressionBoolean EOF
            {
             newCompositeNode(grammarAccess.getExpressionBooleanRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionBoolean=ruleExpressionBoolean();

            state._fsp--;

             current =iv_ruleExpressionBoolean; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionBoolean"


    // $ANTLR start "ruleExpressionBoolean"
    // InternalCal.g:5519:1: ruleExpressionBoolean returns [EObject current=null] : ( (lv_value_0_0= ruleBOOL ) ) ;
    public final EObject ruleExpressionBoolean() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5522:28: ( ( (lv_value_0_0= ruleBOOL ) ) )
            // InternalCal.g:5523:1: ( (lv_value_0_0= ruleBOOL ) )
            {
            // InternalCal.g:5523:1: ( (lv_value_0_0= ruleBOOL ) )
            // InternalCal.g:5524:1: (lv_value_0_0= ruleBOOL )
            {
            // InternalCal.g:5524:1: (lv_value_0_0= ruleBOOL )
            // InternalCal.g:5525:3: lv_value_0_0= ruleBOOL
            {
             
            	        newCompositeNode(grammarAccess.getExpressionBooleanAccess().getValueBOOLParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleBOOL();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExpressionBooleanRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"net.sf.orcc.cal.Cal.BOOL");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionBoolean"


    // $ANTLR start "entryRuleBOOL"
    // InternalCal.g:5549:1: entryRuleBOOL returns [String current=null] : iv_ruleBOOL= ruleBOOL EOF ;
    public final String entryRuleBOOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOL = null;


        try {
            // InternalCal.g:5550:2: (iv_ruleBOOL= ruleBOOL EOF )
            // InternalCal.g:5551:2: iv_ruleBOOL= ruleBOOL EOF
            {
             newCompositeNode(grammarAccess.getBOOLRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBOOL=ruleBOOL();

            state._fsp--;

             current =iv_ruleBOOL.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBOOL"


    // $ANTLR start "ruleBOOL"
    // InternalCal.g:5558:1: ruleBOOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5561:28: ( (kw= 'true' | kw= 'false' ) )
            // InternalCal.g:5562:1: (kw= 'true' | kw= 'false' )
            {
            // InternalCal.g:5562:1: (kw= 'true' | kw= 'false' )
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==82) ) {
                alt113=1;
            }
            else if ( (LA113_0==83) ) {
                alt113=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }
            switch (alt113) {
                case 1 :
                    // InternalCal.g:5563:2: kw= 'true'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getTrueKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:5570:2: kw= 'false'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getFalseKeyword_1()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBOOL"


    // $ANTLR start "entryRuleExpressionFloat"
    // InternalCal.g:5583:1: entryRuleExpressionFloat returns [EObject current=null] : iv_ruleExpressionFloat= ruleExpressionFloat EOF ;
    public final EObject entryRuleExpressionFloat() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionFloat = null;


        try {
            // InternalCal.g:5584:2: (iv_ruleExpressionFloat= ruleExpressionFloat EOF )
            // InternalCal.g:5585:2: iv_ruleExpressionFloat= ruleExpressionFloat EOF
            {
             newCompositeNode(grammarAccess.getExpressionFloatRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionFloat=ruleExpressionFloat();

            state._fsp--;

             current =iv_ruleExpressionFloat; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionFloat"


    // $ANTLR start "ruleExpressionFloat"
    // InternalCal.g:5592:1: ruleExpressionFloat returns [EObject current=null] : ( (lv_value_0_0= RULE_REAL ) ) ;
    public final EObject ruleExpressionFloat() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5595:28: ( ( (lv_value_0_0= RULE_REAL ) ) )
            // InternalCal.g:5596:1: ( (lv_value_0_0= RULE_REAL ) )
            {
            // InternalCal.g:5596:1: ( (lv_value_0_0= RULE_REAL ) )
            // InternalCal.g:5597:1: (lv_value_0_0= RULE_REAL )
            {
            // InternalCal.g:5597:1: (lv_value_0_0= RULE_REAL )
            // InternalCal.g:5598:3: lv_value_0_0= RULE_REAL
            {
            lv_value_0_0=(Token)match(input,RULE_REAL,FOLLOW_2); 

            			newLeafNode(lv_value_0_0, grammarAccess.getExpressionFloatAccess().getValueREALTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getExpressionFloatRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"net.sf.orcc.cal.Cal.REAL");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionFloat"


    // $ANTLR start "entryRuleExpressionInteger"
    // InternalCal.g:5622:1: entryRuleExpressionInteger returns [EObject current=null] : iv_ruleExpressionInteger= ruleExpressionInteger EOF ;
    public final EObject entryRuleExpressionInteger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionInteger = null;


        try {
            // InternalCal.g:5623:2: (iv_ruleExpressionInteger= ruleExpressionInteger EOF )
            // InternalCal.g:5624:2: iv_ruleExpressionInteger= ruleExpressionInteger EOF
            {
             newCompositeNode(grammarAccess.getExpressionIntegerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionInteger=ruleExpressionInteger();

            state._fsp--;

             current =iv_ruleExpressionInteger; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionInteger"


    // $ANTLR start "ruleExpressionInteger"
    // InternalCal.g:5631:1: ruleExpressionInteger returns [EObject current=null] : ( ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) ) ) ;
    public final EObject ruleExpressionInteger() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_1=null;
        Token lv_value_0_2=null;
        Token lv_value_0_3=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5634:28: ( ( ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) ) ) )
            // InternalCal.g:5635:1: ( ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) ) )
            {
            // InternalCal.g:5635:1: ( ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) ) )
            // InternalCal.g:5636:1: ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) )
            {
            // InternalCal.g:5636:1: ( (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX ) )
            // InternalCal.g:5637:1: (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX )
            {
            // InternalCal.g:5637:1: (lv_value_0_1= RULE_DECIMAL | lv_value_0_2= RULE_OCTAL | lv_value_0_3= RULE_HEX )
            int alt114=3;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL:
                {
                alt114=1;
                }
                break;
            case RULE_OCTAL:
                {
                alt114=2;
                }
                break;
            case RULE_HEX:
                {
                alt114=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // InternalCal.g:5638:3: lv_value_0_1= RULE_DECIMAL
                    {
                    lv_value_0_1=(Token)match(input,RULE_DECIMAL,FOLLOW_2); 

                    			newLeafNode(lv_value_0_1, grammarAccess.getExpressionIntegerAccess().getValueDECIMALTerminalRuleCall_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExpressionIntegerRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_0_1, 
                            		"net.sf.orcc.cal.Cal.DECIMAL");
                    	    

                    }
                    break;
                case 2 :
                    // InternalCal.g:5653:8: lv_value_0_2= RULE_OCTAL
                    {
                    lv_value_0_2=(Token)match(input,RULE_OCTAL,FOLLOW_2); 

                    			newLeafNode(lv_value_0_2, grammarAccess.getExpressionIntegerAccess().getValueOCTALTerminalRuleCall_0_1()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExpressionIntegerRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_0_2, 
                            		"net.sf.orcc.cal.Cal.OCTAL");
                    	    

                    }
                    break;
                case 3 :
                    // InternalCal.g:5668:8: lv_value_0_3= RULE_HEX
                    {
                    lv_value_0_3=(Token)match(input,RULE_HEX,FOLLOW_2); 

                    			newLeafNode(lv_value_0_3, grammarAccess.getExpressionIntegerAccess().getValueHEXTerminalRuleCall_0_2()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExpressionIntegerRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_0_3, 
                            		"net.sf.orcc.cal.Cal.HEX");
                    	    

                    }
                    break;

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionInteger"


    // $ANTLR start "entryRuleExpressionString"
    // InternalCal.g:5694:1: entryRuleExpressionString returns [EObject current=null] : iv_ruleExpressionString= ruleExpressionString EOF ;
    public final EObject entryRuleExpressionString() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionString = null;


        try {
            // InternalCal.g:5695:2: (iv_ruleExpressionString= ruleExpressionString EOF )
            // InternalCal.g:5696:2: iv_ruleExpressionString= ruleExpressionString EOF
            {
             newCompositeNode(grammarAccess.getExpressionStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpressionString=ruleExpressionString();

            state._fsp--;

             current =iv_ruleExpressionString; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionString"


    // $ANTLR start "ruleExpressionString"
    // InternalCal.g:5703:1: ruleExpressionString returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleExpressionString() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5706:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // InternalCal.g:5707:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // InternalCal.g:5707:1: ( (lv_value_0_0= RULE_STRING ) )
            // InternalCal.g:5708:1: (lv_value_0_0= RULE_STRING )
            {
            // InternalCal.g:5708:1: (lv_value_0_0= RULE_STRING )
            // InternalCal.g:5709:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            			newLeafNode(lv_value_0_0, grammarAccess.getExpressionStringAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getExpressionStringRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"org.eclipse.xtext.common.Terminals.STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionString"


    // $ANTLR start "entryRuleAstType"
    // InternalCal.g:5733:1: entryRuleAstType returns [EObject current=null] : iv_ruleAstType= ruleAstType EOF ;
    public final EObject entryRuleAstType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstType = null;


        try {
            // InternalCal.g:5734:2: (iv_ruleAstType= ruleAstType EOF )
            // InternalCal.g:5735:2: iv_ruleAstType= ruleAstType EOF
            {
             newCompositeNode(grammarAccess.getAstTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstType=ruleAstType();

            state._fsp--;

             current =iv_ruleAstType; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstType"


    // $ANTLR start "ruleAstType"
    // InternalCal.g:5742:1: ruleAstType returns [EObject current=null] : (this_AstTypeBool_0= ruleAstTypeBool | this_AstTypeFloat_1= ruleAstTypeFloat | this_AstTypeHalf_2= ruleAstTypeHalf | this_AstTypeDouble_3= ruleAstTypeDouble | this_AstTypeInt_4= ruleAstTypeInt | this_AstTypeList_5= ruleAstTypeList | this_AstTypeString_6= ruleAstTypeString | this_AstTypeUint_7= ruleAstTypeUint ) ;
    public final EObject ruleAstType() throws RecognitionException {
        EObject current = null;

        EObject this_AstTypeBool_0 = null;

        EObject this_AstTypeFloat_1 = null;

        EObject this_AstTypeHalf_2 = null;

        EObject this_AstTypeDouble_3 = null;

        EObject this_AstTypeInt_4 = null;

        EObject this_AstTypeList_5 = null;

        EObject this_AstTypeString_6 = null;

        EObject this_AstTypeUint_7 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5745:28: ( (this_AstTypeBool_0= ruleAstTypeBool | this_AstTypeFloat_1= ruleAstTypeFloat | this_AstTypeHalf_2= ruleAstTypeHalf | this_AstTypeDouble_3= ruleAstTypeDouble | this_AstTypeInt_4= ruleAstTypeInt | this_AstTypeList_5= ruleAstTypeList | this_AstTypeString_6= ruleAstTypeString | this_AstTypeUint_7= ruleAstTypeUint ) )
            // InternalCal.g:5746:1: (this_AstTypeBool_0= ruleAstTypeBool | this_AstTypeFloat_1= ruleAstTypeFloat | this_AstTypeHalf_2= ruleAstTypeHalf | this_AstTypeDouble_3= ruleAstTypeDouble | this_AstTypeInt_4= ruleAstTypeInt | this_AstTypeList_5= ruleAstTypeList | this_AstTypeString_6= ruleAstTypeString | this_AstTypeUint_7= ruleAstTypeUint )
            {
            // InternalCal.g:5746:1: (this_AstTypeBool_0= ruleAstTypeBool | this_AstTypeFloat_1= ruleAstTypeFloat | this_AstTypeHalf_2= ruleAstTypeHalf | this_AstTypeDouble_3= ruleAstTypeDouble | this_AstTypeInt_4= ruleAstTypeInt | this_AstTypeList_5= ruleAstTypeList | this_AstTypeString_6= ruleAstTypeString | this_AstTypeUint_7= ruleAstTypeUint )
            int alt115=8;
            switch ( input.LA(1) ) {
            case 84:
                {
                alt115=1;
                }
                break;
            case 85:
                {
                alt115=2;
                }
                break;
            case 86:
                {
                alt115=3;
                }
                break;
            case 87:
                {
                alt115=4;
                }
                break;
            case 88:
                {
                alt115=5;
                }
                break;
            case 90:
                {
                alt115=6;
                }
                break;
            case 92:
                {
                alt115=7;
                }
                break;
            case 93:
                {
                alt115=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }

            switch (alt115) {
                case 1 :
                    // InternalCal.g:5747:5: this_AstTypeBool_0= ruleAstTypeBool
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeBoolParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeBool_0=ruleAstTypeBool();

                    state._fsp--;

                     
                            current = this_AstTypeBool_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // InternalCal.g:5757:5: this_AstTypeFloat_1= ruleAstTypeFloat
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeFloatParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeFloat_1=ruleAstTypeFloat();

                    state._fsp--;

                     
                            current = this_AstTypeFloat_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // InternalCal.g:5767:5: this_AstTypeHalf_2= ruleAstTypeHalf
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeHalfParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeHalf_2=ruleAstTypeHalf();

                    state._fsp--;

                     
                            current = this_AstTypeHalf_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // InternalCal.g:5777:5: this_AstTypeDouble_3= ruleAstTypeDouble
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeDoubleParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeDouble_3=ruleAstTypeDouble();

                    state._fsp--;

                     
                            current = this_AstTypeDouble_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // InternalCal.g:5787:5: this_AstTypeInt_4= ruleAstTypeInt
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeIntParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeInt_4=ruleAstTypeInt();

                    state._fsp--;

                     
                            current = this_AstTypeInt_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // InternalCal.g:5797:5: this_AstTypeList_5= ruleAstTypeList
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeListParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeList_5=ruleAstTypeList();

                    state._fsp--;

                     
                            current = this_AstTypeList_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // InternalCal.g:5807:5: this_AstTypeString_6= ruleAstTypeString
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeStringParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeString_6=ruleAstTypeString();

                    state._fsp--;

                     
                            current = this_AstTypeString_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // InternalCal.g:5817:5: this_AstTypeUint_7= ruleAstTypeUint
                    {
                     
                            newCompositeNode(grammarAccess.getAstTypeAccess().getAstTypeUintParserRuleCall_7()); 
                        
                    pushFollow(FOLLOW_2);
                    this_AstTypeUint_7=ruleAstTypeUint();

                    state._fsp--;

                     
                            current = this_AstTypeUint_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstType"


    // $ANTLR start "entryRuleAstTypeBool"
    // InternalCal.g:5833:1: entryRuleAstTypeBool returns [EObject current=null] : iv_ruleAstTypeBool= ruleAstTypeBool EOF ;
    public final EObject entryRuleAstTypeBool() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeBool = null;


        try {
            // InternalCal.g:5834:2: (iv_ruleAstTypeBool= ruleAstTypeBool EOF )
            // InternalCal.g:5835:2: iv_ruleAstTypeBool= ruleAstTypeBool EOF
            {
             newCompositeNode(grammarAccess.getAstTypeBoolRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeBool=ruleAstTypeBool();

            state._fsp--;

             current =iv_ruleAstTypeBool; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeBool"


    // $ANTLR start "ruleAstTypeBool"
    // InternalCal.g:5842:1: ruleAstTypeBool returns [EObject current=null] : ( () otherlv_1= 'bool' ) ;
    public final EObject ruleAstTypeBool() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5845:28: ( ( () otherlv_1= 'bool' ) )
            // InternalCal.g:5846:1: ( () otherlv_1= 'bool' )
            {
            // InternalCal.g:5846:1: ( () otherlv_1= 'bool' )
            // InternalCal.g:5846:2: () otherlv_1= 'bool'
            {
            // InternalCal.g:5846:2: ()
            // InternalCal.g:5847:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeBoolAccess().getAstTypeBoolAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,84,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeBoolAccess().getBoolKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeBool"


    // $ANTLR start "entryRuleAstTypeFloat"
    // InternalCal.g:5864:1: entryRuleAstTypeFloat returns [EObject current=null] : iv_ruleAstTypeFloat= ruleAstTypeFloat EOF ;
    public final EObject entryRuleAstTypeFloat() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeFloat = null;


        try {
            // InternalCal.g:5865:2: (iv_ruleAstTypeFloat= ruleAstTypeFloat EOF )
            // InternalCal.g:5866:2: iv_ruleAstTypeFloat= ruleAstTypeFloat EOF
            {
             newCompositeNode(grammarAccess.getAstTypeFloatRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeFloat=ruleAstTypeFloat();

            state._fsp--;

             current =iv_ruleAstTypeFloat; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeFloat"


    // $ANTLR start "ruleAstTypeFloat"
    // InternalCal.g:5873:1: ruleAstTypeFloat returns [EObject current=null] : ( () otherlv_1= 'float' ) ;
    public final EObject ruleAstTypeFloat() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5876:28: ( ( () otherlv_1= 'float' ) )
            // InternalCal.g:5877:1: ( () otherlv_1= 'float' )
            {
            // InternalCal.g:5877:1: ( () otherlv_1= 'float' )
            // InternalCal.g:5877:2: () otherlv_1= 'float'
            {
            // InternalCal.g:5877:2: ()
            // InternalCal.g:5878:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeFloatAccess().getAstTypeFloatAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,85,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeFloatAccess().getFloatKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeFloat"


    // $ANTLR start "entryRuleAstTypeHalf"
    // InternalCal.g:5895:1: entryRuleAstTypeHalf returns [EObject current=null] : iv_ruleAstTypeHalf= ruleAstTypeHalf EOF ;
    public final EObject entryRuleAstTypeHalf() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeHalf = null;


        try {
            // InternalCal.g:5896:2: (iv_ruleAstTypeHalf= ruleAstTypeHalf EOF )
            // InternalCal.g:5897:2: iv_ruleAstTypeHalf= ruleAstTypeHalf EOF
            {
             newCompositeNode(grammarAccess.getAstTypeHalfRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeHalf=ruleAstTypeHalf();

            state._fsp--;

             current =iv_ruleAstTypeHalf; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeHalf"


    // $ANTLR start "ruleAstTypeHalf"
    // InternalCal.g:5904:1: ruleAstTypeHalf returns [EObject current=null] : ( () otherlv_1= 'half' ) ;
    public final EObject ruleAstTypeHalf() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5907:28: ( ( () otherlv_1= 'half' ) )
            // InternalCal.g:5908:1: ( () otherlv_1= 'half' )
            {
            // InternalCal.g:5908:1: ( () otherlv_1= 'half' )
            // InternalCal.g:5908:2: () otherlv_1= 'half'
            {
            // InternalCal.g:5908:2: ()
            // InternalCal.g:5909:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeHalfAccess().getAstTypeHalfAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,86,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeHalfAccess().getHalfKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeHalf"


    // $ANTLR start "entryRuleAstTypeDouble"
    // InternalCal.g:5926:1: entryRuleAstTypeDouble returns [EObject current=null] : iv_ruleAstTypeDouble= ruleAstTypeDouble EOF ;
    public final EObject entryRuleAstTypeDouble() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeDouble = null;


        try {
            // InternalCal.g:5927:2: (iv_ruleAstTypeDouble= ruleAstTypeDouble EOF )
            // InternalCal.g:5928:2: iv_ruleAstTypeDouble= ruleAstTypeDouble EOF
            {
             newCompositeNode(grammarAccess.getAstTypeDoubleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeDouble=ruleAstTypeDouble();

            state._fsp--;

             current =iv_ruleAstTypeDouble; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeDouble"


    // $ANTLR start "ruleAstTypeDouble"
    // InternalCal.g:5935:1: ruleAstTypeDouble returns [EObject current=null] : ( () otherlv_1= 'double' ) ;
    public final EObject ruleAstTypeDouble() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalCal.g:5938:28: ( ( () otherlv_1= 'double' ) )
            // InternalCal.g:5939:1: ( () otherlv_1= 'double' )
            {
            // InternalCal.g:5939:1: ( () otherlv_1= 'double' )
            // InternalCal.g:5939:2: () otherlv_1= 'double'
            {
            // InternalCal.g:5939:2: ()
            // InternalCal.g:5940:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeDoubleAccess().getAstTypeDoubleAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,87,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeDoubleAccess().getDoubleKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeDouble"


    // $ANTLR start "entryRuleAstTypeInt"
    // InternalCal.g:5957:1: entryRuleAstTypeInt returns [EObject current=null] : iv_ruleAstTypeInt= ruleAstTypeInt EOF ;
    public final EObject entryRuleAstTypeInt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeInt = null;


        try {
            // InternalCal.g:5958:2: (iv_ruleAstTypeInt= ruleAstTypeInt EOF )
            // InternalCal.g:5959:2: iv_ruleAstTypeInt= ruleAstTypeInt EOF
            {
             newCompositeNode(grammarAccess.getAstTypeIntRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeInt=ruleAstTypeInt();

            state._fsp--;

             current =iv_ruleAstTypeInt; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeInt"


    // $ANTLR start "ruleAstTypeInt"
    // InternalCal.g:5966:1: ruleAstTypeInt returns [EObject current=null] : ( () otherlv_1= 'int' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? ) ;
    public final EObject ruleAstTypeInt() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_size_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:5969:28: ( ( () otherlv_1= 'int' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? ) )
            // InternalCal.g:5970:1: ( () otherlv_1= 'int' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? )
            {
            // InternalCal.g:5970:1: ( () otherlv_1= 'int' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? )
            // InternalCal.g:5970:2: () otherlv_1= 'int' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )?
            {
            // InternalCal.g:5970:2: ()
            // InternalCal.g:5971:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeIntAccess().getAstTypeIntAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,88,FOLLOW_87); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeIntAccess().getIntKeyword_1());
                
            // InternalCal.g:5980:1: (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==25) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // InternalCal.g:5980:3: otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')'
                    {
                    otherlv_2=(Token)match(input,25,FOLLOW_88); 

                        	newLeafNode(otherlv_2, grammarAccess.getAstTypeIntAccess().getLeftParenthesisKeyword_2_0());
                        
                    otherlv_3=(Token)match(input,89,FOLLOW_12); 

                        	newLeafNode(otherlv_3, grammarAccess.getAstTypeIntAccess().getSizeKeyword_2_1());
                        
                    otherlv_4=(Token)match(input,24,FOLLOW_13); 

                        	newLeafNode(otherlv_4, grammarAccess.getAstTypeIntAccess().getEqualsSignKeyword_2_2());
                        
                    // InternalCal.g:5992:1: ( (lv_size_5_0= ruleAstExpression ) )
                    // InternalCal.g:5993:1: (lv_size_5_0= ruleAstExpression )
                    {
                    // InternalCal.g:5993:1: (lv_size_5_0= ruleAstExpression )
                    // InternalCal.g:5994:3: lv_size_5_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstTypeIntAccess().getSizeAstExpressionParserRuleCall_2_3_0()); 
                    	    
                    pushFollow(FOLLOW_37);
                    lv_size_5_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstTypeIntRule());
                    	        }
                           		set(
                           			current, 
                           			"size",
                            		lv_size_5_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_6=(Token)match(input,27,FOLLOW_2); 

                        	newLeafNode(otherlv_6, grammarAccess.getAstTypeIntAccess().getRightParenthesisKeyword_2_4());
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeInt"


    // $ANTLR start "entryRuleAstTypeList"
    // InternalCal.g:6022:1: entryRuleAstTypeList returns [EObject current=null] : iv_ruleAstTypeList= ruleAstTypeList EOF ;
    public final EObject entryRuleAstTypeList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeList = null;


        try {
            // InternalCal.g:6023:2: (iv_ruleAstTypeList= ruleAstTypeList EOF )
            // InternalCal.g:6024:2: iv_ruleAstTypeList= ruleAstTypeList EOF
            {
             newCompositeNode(grammarAccess.getAstTypeListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeList=ruleAstTypeList();

            state._fsp--;

             current =iv_ruleAstTypeList; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeList"


    // $ANTLR start "ruleAstTypeList"
    // InternalCal.g:6031:1: ruleAstTypeList returns [EObject current=null] : (otherlv_0= 'List' otherlv_1= '(' otherlv_2= 'type' otherlv_3= ':' ( (lv_type_4_0= ruleAstType ) ) otherlv_5= ',' otherlv_6= 'size' otherlv_7= '=' ( (lv_size_8_0= ruleAstExpression ) ) otherlv_9= ')' ) ;
    public final EObject ruleAstTypeList() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_type_4_0 = null;

        EObject lv_size_8_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:6034:28: ( (otherlv_0= 'List' otherlv_1= '(' otherlv_2= 'type' otherlv_3= ':' ( (lv_type_4_0= ruleAstType ) ) otherlv_5= ',' otherlv_6= 'size' otherlv_7= '=' ( (lv_size_8_0= ruleAstExpression ) ) otherlv_9= ')' ) )
            // InternalCal.g:6035:1: (otherlv_0= 'List' otherlv_1= '(' otherlv_2= 'type' otherlv_3= ':' ( (lv_type_4_0= ruleAstType ) ) otherlv_5= ',' otherlv_6= 'size' otherlv_7= '=' ( (lv_size_8_0= ruleAstExpression ) ) otherlv_9= ')' )
            {
            // InternalCal.g:6035:1: (otherlv_0= 'List' otherlv_1= '(' otherlv_2= 'type' otherlv_3= ':' ( (lv_type_4_0= ruleAstType ) ) otherlv_5= ',' otherlv_6= 'size' otherlv_7= '=' ( (lv_size_8_0= ruleAstExpression ) ) otherlv_9= ')' )
            // InternalCal.g:6035:3: otherlv_0= 'List' otherlv_1= '(' otherlv_2= 'type' otherlv_3= ':' ( (lv_type_4_0= ruleAstType ) ) otherlv_5= ',' otherlv_6= 'size' otherlv_7= '=' ( (lv_size_8_0= ruleAstExpression ) ) otherlv_9= ')'
            {
            otherlv_0=(Token)match(input,90,FOLLOW_7); 

                	newLeafNode(otherlv_0, grammarAccess.getAstTypeListAccess().getListKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_89); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeListAccess().getLeftParenthesisKeyword_1());
                
            otherlv_2=(Token)match(input,91,FOLLOW_8); 

                	newLeafNode(otherlv_2, grammarAccess.getAstTypeListAccess().getTypeKeyword_2());
                
            otherlv_3=(Token)match(input,22,FOLLOW_16); 

                	newLeafNode(otherlv_3, grammarAccess.getAstTypeListAccess().getColonKeyword_3());
                
            // InternalCal.g:6051:1: ( (lv_type_4_0= ruleAstType ) )
            // InternalCal.g:6052:1: (lv_type_4_0= ruleAstType )
            {
            // InternalCal.g:6052:1: (lv_type_4_0= ruleAstType )
            // InternalCal.g:6053:3: lv_type_4_0= ruleAstType
            {
             
            	        newCompositeNode(grammarAccess.getAstTypeListAccess().getTypeAstTypeParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_90);
            lv_type_4_0=ruleAstType();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAstTypeListRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_4_0, 
                    		"net.sf.orcc.cal.Cal.AstType");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_5=(Token)match(input,26,FOLLOW_88); 

                	newLeafNode(otherlv_5, grammarAccess.getAstTypeListAccess().getCommaKeyword_5());
                
            otherlv_6=(Token)match(input,89,FOLLOW_12); 

                	newLeafNode(otherlv_6, grammarAccess.getAstTypeListAccess().getSizeKeyword_6());
                
            otherlv_7=(Token)match(input,24,FOLLOW_13); 

                	newLeafNode(otherlv_7, grammarAccess.getAstTypeListAccess().getEqualsSignKeyword_7());
                
            // InternalCal.g:6081:1: ( (lv_size_8_0= ruleAstExpression ) )
            // InternalCal.g:6082:1: (lv_size_8_0= ruleAstExpression )
            {
            // InternalCal.g:6082:1: (lv_size_8_0= ruleAstExpression )
            // InternalCal.g:6083:3: lv_size_8_0= ruleAstExpression
            {
             
            	        newCompositeNode(grammarAccess.getAstTypeListAccess().getSizeAstExpressionParserRuleCall_8_0()); 
            	    
            pushFollow(FOLLOW_37);
            lv_size_8_0=ruleAstExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAstTypeListRule());
            	        }
                   		set(
                   			current, 
                   			"size",
                    		lv_size_8_0, 
                    		"net.sf.orcc.cal.Cal.AstExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_9=(Token)match(input,27,FOLLOW_2); 

                	newLeafNode(otherlv_9, grammarAccess.getAstTypeListAccess().getRightParenthesisKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeList"


    // $ANTLR start "entryRuleAstTypeString"
    // InternalCal.g:6111:1: entryRuleAstTypeString returns [EObject current=null] : iv_ruleAstTypeString= ruleAstTypeString EOF ;
    public final EObject entryRuleAstTypeString() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeString = null;


        try {
            // InternalCal.g:6112:2: (iv_ruleAstTypeString= ruleAstTypeString EOF )
            // InternalCal.g:6113:2: iv_ruleAstTypeString= ruleAstTypeString EOF
            {
             newCompositeNode(grammarAccess.getAstTypeStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeString=ruleAstTypeString();

            state._fsp--;

             current =iv_ruleAstTypeString; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeString"


    // $ANTLR start "ruleAstTypeString"
    // InternalCal.g:6120:1: ruleAstTypeString returns [EObject current=null] : ( () otherlv_1= 'String' ) ;
    public final EObject ruleAstTypeString() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalCal.g:6123:28: ( ( () otherlv_1= 'String' ) )
            // InternalCal.g:6124:1: ( () otherlv_1= 'String' )
            {
            // InternalCal.g:6124:1: ( () otherlv_1= 'String' )
            // InternalCal.g:6124:2: () otherlv_1= 'String'
            {
            // InternalCal.g:6124:2: ()
            // InternalCal.g:6125:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeStringAccess().getAstTypeStringAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,92,FOLLOW_2); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeStringAccess().getStringKeyword_1());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeString"


    // $ANTLR start "entryRuleAstTypeUint"
    // InternalCal.g:6142:1: entryRuleAstTypeUint returns [EObject current=null] : iv_ruleAstTypeUint= ruleAstTypeUint EOF ;
    public final EObject entryRuleAstTypeUint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstTypeUint = null;


        try {
            // InternalCal.g:6143:2: (iv_ruleAstTypeUint= ruleAstTypeUint EOF )
            // InternalCal.g:6144:2: iv_ruleAstTypeUint= ruleAstTypeUint EOF
            {
             newCompositeNode(grammarAccess.getAstTypeUintRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstTypeUint=ruleAstTypeUint();

            state._fsp--;

             current =iv_ruleAstTypeUint; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstTypeUint"


    // $ANTLR start "ruleAstTypeUint"
    // InternalCal.g:6151:1: ruleAstTypeUint returns [EObject current=null] : ( () otherlv_1= 'uint' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? ) ;
    public final EObject ruleAstTypeUint() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_size_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:6154:28: ( ( () otherlv_1= 'uint' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? ) )
            // InternalCal.g:6155:1: ( () otherlv_1= 'uint' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? )
            {
            // InternalCal.g:6155:1: ( () otherlv_1= 'uint' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )? )
            // InternalCal.g:6155:2: () otherlv_1= 'uint' (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )?
            {
            // InternalCal.g:6155:2: ()
            // InternalCal.g:6156:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAstTypeUintAccess().getAstTypeUintAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,93,FOLLOW_87); 

                	newLeafNode(otherlv_1, grammarAccess.getAstTypeUintAccess().getUintKeyword_1());
                
            // InternalCal.g:6165:1: (otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')' )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==25) ) {
                alt117=1;
            }
            switch (alt117) {
                case 1 :
                    // InternalCal.g:6165:3: otherlv_2= '(' otherlv_3= 'size' otherlv_4= '=' ( (lv_size_5_0= ruleAstExpression ) ) otherlv_6= ')'
                    {
                    otherlv_2=(Token)match(input,25,FOLLOW_88); 

                        	newLeafNode(otherlv_2, grammarAccess.getAstTypeUintAccess().getLeftParenthesisKeyword_2_0());
                        
                    otherlv_3=(Token)match(input,89,FOLLOW_12); 

                        	newLeafNode(otherlv_3, grammarAccess.getAstTypeUintAccess().getSizeKeyword_2_1());
                        
                    otherlv_4=(Token)match(input,24,FOLLOW_13); 

                        	newLeafNode(otherlv_4, grammarAccess.getAstTypeUintAccess().getEqualsSignKeyword_2_2());
                        
                    // InternalCal.g:6177:1: ( (lv_size_5_0= ruleAstExpression ) )
                    // InternalCal.g:6178:1: (lv_size_5_0= ruleAstExpression )
                    {
                    // InternalCal.g:6178:1: (lv_size_5_0= ruleAstExpression )
                    // InternalCal.g:6179:3: lv_size_5_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstTypeUintAccess().getSizeAstExpressionParserRuleCall_2_3_0()); 
                    	    
                    pushFollow(FOLLOW_37);
                    lv_size_5_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstTypeUintRule());
                    	        }
                           		set(
                           			current, 
                           			"size",
                            		lv_size_5_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_6=(Token)match(input,27,FOLLOW_2); 

                        	newLeafNode(otherlv_6, grammarAccess.getAstTypeUintAccess().getRightParenthesisKeyword_2_4());
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstTypeUint"


    // $ANTLR start "entryRuleVariableDeclaration"
    // InternalCal.g:6207:1: entryRuleVariableDeclaration returns [EObject current=null] : iv_ruleVariableDeclaration= ruleVariableDeclaration EOF ;
    public final EObject entryRuleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDeclaration = null;


        try {
            // InternalCal.g:6208:2: (iv_ruleVariableDeclaration= ruleVariableDeclaration EOF )
            // InternalCal.g:6209:2: iv_ruleVariableDeclaration= ruleVariableDeclaration EOF
            {
             newCompositeNode(grammarAccess.getVariableDeclarationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableDeclaration=ruleVariableDeclaration();

            state._fsp--;

             current =iv_ruleVariableDeclaration; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableDeclaration"


    // $ANTLR start "ruleVariableDeclaration"
    // InternalCal.g:6216:1: ruleVariableDeclaration returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )* ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_type_1_0 = null;

        EObject lv_dimensions_4_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:6219:28: ( ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )* ) )
            // InternalCal.g:6220:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )* )
            {
            // InternalCal.g:6220:1: ( ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )* )
            // InternalCal.g:6220:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )* ( (lv_type_1_0= ruleAstType ) ) ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )*
            {
            // InternalCal.g:6220:2: ( (lv_annotations_0_0= ruleAstAnnotation ) )*
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( (LA118_0==94) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // InternalCal.g:6221:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    {
            	    // InternalCal.g:6221:1: (lv_annotations_0_0= ruleAstAnnotation )
            	    // InternalCal.g:6222:3: lv_annotations_0_0= ruleAstAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getAnnotationsAstAnnotationParserRuleCall_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_16);
            	    lv_annotations_0_0=ruleAstAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_0_0, 
            	            		"net.sf.orcc.cal.Cal.AstAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop118;
                }
            } while (true);

            // InternalCal.g:6238:3: ( (lv_type_1_0= ruleAstType ) )
            // InternalCal.g:6239:1: (lv_type_1_0= ruleAstType )
            {
            // InternalCal.g:6239:1: (lv_type_1_0= ruleAstType )
            // InternalCal.g:6240:3: lv_type_1_0= ruleAstType
            {
             
            	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getTypeAstTypeParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_3);
            lv_type_1_0=ruleAstType();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_1_0, 
                    		"net.sf.orcc.cal.Cal.AstType");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // InternalCal.g:6256:2: ( (lv_name_2_0= RULE_ID ) )
            // InternalCal.g:6257:1: (lv_name_2_0= RULE_ID )
            {
            // InternalCal.g:6257:1: (lv_name_2_0= RULE_ID )
            // InternalCal.g:6258:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_83); 

            			newLeafNode(lv_name_2_0, grammarAccess.getVariableDeclarationAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableDeclarationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            // InternalCal.g:6274:2: (otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']' )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==48) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // InternalCal.g:6274:4: otherlv_3= '[' ( (lv_dimensions_4_0= ruleAstExpression ) ) otherlv_5= ']'
            	    {
            	    otherlv_3=(Token)match(input,48,FOLLOW_13); 

            	        	newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationAccess().getLeftSquareBracketKeyword_3_0());
            	        
            	    // InternalCal.g:6278:1: ( (lv_dimensions_4_0= ruleAstExpression ) )
            	    // InternalCal.g:6279:1: (lv_dimensions_4_0= ruleAstExpression )
            	    {
            	    // InternalCal.g:6279:1: (lv_dimensions_4_0= ruleAstExpression )
            	    // InternalCal.g:6280:3: lv_dimensions_4_0= ruleAstExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getDimensionsAstExpressionParserRuleCall_3_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_61);
            	    lv_dimensions_4_0=ruleAstExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"dimensions",
            	            		lv_dimensions_4_0, 
            	            		"net.sf.orcc.cal.Cal.AstExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }

            	    otherlv_5=(Token)match(input,49,FOLLOW_83); 

            	        	newLeafNode(otherlv_5, grammarAccess.getVariableDeclarationAccess().getRightSquareBracketKeyword_3_2());
            	        

            	    }
            	    break;

            	default :
            	    break loop119;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableDeclaration"


    // $ANTLR start "entryRuleValuedVariableDeclaration"
    // InternalCal.g:6308:1: entryRuleValuedVariableDeclaration returns [EObject current=null] : iv_ruleValuedVariableDeclaration= ruleValuedVariableDeclaration EOF ;
    public final EObject entryRuleValuedVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValuedVariableDeclaration = null;


        try {
            // InternalCal.g:6309:2: (iv_ruleValuedVariableDeclaration= ruleValuedVariableDeclaration EOF )
            // InternalCal.g:6310:2: iv_ruleValuedVariableDeclaration= ruleValuedVariableDeclaration EOF
            {
             newCompositeNode(grammarAccess.getValuedVariableDeclarationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValuedVariableDeclaration=ruleValuedVariableDeclaration();

            state._fsp--;

             current =iv_ruleValuedVariableDeclaration; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValuedVariableDeclaration"


    // $ANTLR start "ruleValuedVariableDeclaration"
    // InternalCal.g:6317:1: ruleValuedVariableDeclaration returns [EObject current=null] : (this_VariableDeclaration_0= ruleVariableDeclaration ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )? ) ;
    public final EObject ruleValuedVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_constant_1_0=null;
        Token otherlv_2=null;
        EObject this_VariableDeclaration_0 = null;

        EObject lv_value_3_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:6320:28: ( (this_VariableDeclaration_0= ruleVariableDeclaration ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )? ) )
            // InternalCal.g:6321:1: (this_VariableDeclaration_0= ruleVariableDeclaration ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )? )
            {
            // InternalCal.g:6321:1: (this_VariableDeclaration_0= ruleVariableDeclaration ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )? )
            // InternalCal.g:6322:5: this_VariableDeclaration_0= ruleVariableDeclaration ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )?
            {
             
                    newCompositeNode(grammarAccess.getValuedVariableDeclarationAccess().getVariableDeclarationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_91);
            this_VariableDeclaration_0=ruleVariableDeclaration();

            state._fsp--;

             
                    current = this_VariableDeclaration_0; 
                    afterParserOrEnumRuleCall();
                
            // InternalCal.g:6330:1: ( ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) ) )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==24||LA121_0==51) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // InternalCal.g:6330:2: ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' ) ( (lv_value_3_0= ruleAstExpression ) )
                    {
                    // InternalCal.g:6330:2: ( ( (lv_constant_1_0= '=' ) ) | otherlv_2= ':=' )
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==24) ) {
                        alt120=1;
                    }
                    else if ( (LA120_0==51) ) {
                        alt120=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 120, 0, input);

                        throw nvae;
                    }
                    switch (alt120) {
                        case 1 :
                            // InternalCal.g:6330:3: ( (lv_constant_1_0= '=' ) )
                            {
                            // InternalCal.g:6330:3: ( (lv_constant_1_0= '=' ) )
                            // InternalCal.g:6331:1: (lv_constant_1_0= '=' )
                            {
                            // InternalCal.g:6331:1: (lv_constant_1_0= '=' )
                            // InternalCal.g:6332:3: lv_constant_1_0= '='
                            {
                            lv_constant_1_0=(Token)match(input,24,FOLLOW_13); 

                                    newLeafNode(lv_constant_1_0, grammarAccess.getValuedVariableDeclarationAccess().getConstantEqualsSignKeyword_1_0_0_0());
                                

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getValuedVariableDeclarationRule());
                            	        }
                                   		setWithLastConsumed(current, "constant", true, "=");
                            	    

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalCal.g:6346:7: otherlv_2= ':='
                            {
                            otherlv_2=(Token)match(input,51,FOLLOW_13); 

                                	newLeafNode(otherlv_2, grammarAccess.getValuedVariableDeclarationAccess().getColonEqualsSignKeyword_1_0_1());
                                

                            }
                            break;

                    }

                    // InternalCal.g:6350:2: ( (lv_value_3_0= ruleAstExpression ) )
                    // InternalCal.g:6351:1: (lv_value_3_0= ruleAstExpression )
                    {
                    // InternalCal.g:6351:1: (lv_value_3_0= ruleAstExpression )
                    // InternalCal.g:6352:3: lv_value_3_0= ruleAstExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getValuedVariableDeclarationAccess().getValueAstExpressionParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_2);
                    lv_value_3_0=ruleAstExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getValuedVariableDeclarationRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_3_0, 
                            		"net.sf.orcc.cal.Cal.AstExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValuedVariableDeclaration"


    // $ANTLR start "entryRuleVariableReference"
    // InternalCal.g:6376:1: entryRuleVariableReference returns [EObject current=null] : iv_ruleVariableReference= ruleVariableReference EOF ;
    public final EObject entryRuleVariableReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableReference = null;


        try {
            // InternalCal.g:6377:2: (iv_ruleVariableReference= ruleVariableReference EOF )
            // InternalCal.g:6378:2: iv_ruleVariableReference= ruleVariableReference EOF
            {
             newCompositeNode(grammarAccess.getVariableReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableReference=ruleVariableReference();

            state._fsp--;

             current =iv_ruleVariableReference; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableReference"


    // $ANTLR start "ruleVariableReference"
    // InternalCal.g:6385:1: ruleVariableReference returns [EObject current=null] : ( ( ruleQualifiedName ) ) ;
    public final EObject ruleVariableReference() throws RecognitionException {
        EObject current = null;

         enterRule(); 
            
        try {
            // InternalCal.g:6388:28: ( ( ( ruleQualifiedName ) ) )
            // InternalCal.g:6389:1: ( ( ruleQualifiedName ) )
            {
            // InternalCal.g:6389:1: ( ( ruleQualifiedName ) )
            // InternalCal.g:6390:1: ( ruleQualifiedName )
            {
            // InternalCal.g:6390:1: ( ruleQualifiedName )
            // InternalCal.g:6391:3: ruleQualifiedName
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableReferenceRule());
            	        }
                    
             
            	        newCompositeNode(grammarAccess.getVariableReferenceAccess().getVariableVariableCrossReference_0()); 
            	    
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableReference"


    // $ANTLR start "entryRuleAstAnnotation"
    // InternalCal.g:6412:1: entryRuleAstAnnotation returns [EObject current=null] : iv_ruleAstAnnotation= ruleAstAnnotation EOF ;
    public final EObject entryRuleAstAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAstAnnotation = null;


        try {
            // InternalCal.g:6413:2: (iv_ruleAstAnnotation= ruleAstAnnotation EOF )
            // InternalCal.g:6414:2: iv_ruleAstAnnotation= ruleAstAnnotation EOF
            {
             newCompositeNode(grammarAccess.getAstAnnotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAstAnnotation=ruleAstAnnotation();

            state._fsp--;

             current =iv_ruleAstAnnotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAstAnnotation"


    // $ANTLR start "ruleAstAnnotation"
    // InternalCal.g:6421:1: ruleAstAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )? ) ;
    public final EObject ruleAstAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_arguments_3_0 = null;

        EObject lv_arguments_5_0 = null;


         enterRule(); 
            
        try {
            // InternalCal.g:6424:28: ( (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )? ) )
            // InternalCal.g:6425:1: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )? )
            {
            // InternalCal.g:6425:1: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )? )
            // InternalCal.g:6425:3: otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )?
            {
            otherlv_0=(Token)match(input,94,FOLLOW_3); 

                	newLeafNode(otherlv_0, grammarAccess.getAstAnnotationAccess().getCommercialAtKeyword_0());
                
            // InternalCal.g:6429:1: ( (lv_name_1_0= RULE_ID ) )
            // InternalCal.g:6430:1: (lv_name_1_0= RULE_ID )
            {
            // InternalCal.g:6430:1: (lv_name_1_0= RULE_ID )
            // InternalCal.g:6431:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_87); 

            			newLeafNode(lv_name_1_0, grammarAccess.getAstAnnotationAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAstAnnotationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            // InternalCal.g:6447:2: (otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')' )?
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==25) ) {
                alt123=1;
            }
            switch (alt123) {
                case 1 :
                    // InternalCal.g:6447:4: otherlv_2= '(' ( (lv_arguments_3_0= ruleAnnotationArgument ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )* otherlv_6= ')'
                    {
                    otherlv_2=(Token)match(input,25,FOLLOW_3); 

                        	newLeafNode(otherlv_2, grammarAccess.getAstAnnotationAccess().getLeftParenthesisKeyword_2_0());
                        
                    // InternalCal.g:6451:1: ( (lv_arguments_3_0= ruleAnnotationArgument ) )
                    // InternalCal.g:6452:1: (lv_arguments_3_0= ruleAnnotationArgument )
                    {
                    // InternalCal.g:6452:1: (lv_arguments_3_0= ruleAnnotationArgument )
                    // InternalCal.g:6453:3: lv_arguments_3_0= ruleAnnotationArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getAstAnnotationAccess().getArgumentsAnnotationArgumentParserRuleCall_2_1_0()); 
                    	    
                    pushFollow(FOLLOW_15);
                    lv_arguments_3_0=ruleAnnotationArgument();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAstAnnotationRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_3_0, 
                            		"net.sf.orcc.cal.Cal.AnnotationArgument");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // InternalCal.g:6469:2: (otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) ) )*
                    loop122:
                    do {
                        int alt122=2;
                        int LA122_0 = input.LA(1);

                        if ( (LA122_0==26) ) {
                            alt122=1;
                        }


                        switch (alt122) {
                    	case 1 :
                    	    // InternalCal.g:6469:4: otherlv_4= ',' ( (lv_arguments_5_0= ruleAnnotationArgument ) )
                    	    {
                    	    otherlv_4=(Token)match(input,26,FOLLOW_3); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getAstAnnotationAccess().getCommaKeyword_2_2_0());
                    	        
                    	    // InternalCal.g:6473:1: ( (lv_arguments_5_0= ruleAnnotationArgument ) )
                    	    // InternalCal.g:6474:1: (lv_arguments_5_0= ruleAnnotationArgument )
                    	    {
                    	    // InternalCal.g:6474:1: (lv_arguments_5_0= ruleAnnotationArgument )
                    	    // InternalCal.g:6475:3: lv_arguments_5_0= ruleAnnotationArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAstAnnotationAccess().getArgumentsAnnotationArgumentParserRuleCall_2_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_15);
                    	    lv_arguments_5_0=ruleAnnotationArgument();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAstAnnotationRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"arguments",
                    	            		lv_arguments_5_0, 
                    	            		"net.sf.orcc.cal.Cal.AnnotationArgument");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop122;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,27,FOLLOW_2); 

                        	newLeafNode(otherlv_6, grammarAccess.getAstAnnotationAccess().getRightParenthesisKeyword_2_3());
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAstAnnotation"


    // $ANTLR start "entryRuleAnnotationArgument"
    // InternalCal.g:6503:1: entryRuleAnnotationArgument returns [EObject current=null] : iv_ruleAnnotationArgument= ruleAnnotationArgument EOF ;
    public final EObject entryRuleAnnotationArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotationArgument = null;


        try {
            // InternalCal.g:6504:2: (iv_ruleAnnotationArgument= ruleAnnotationArgument EOF )
            // InternalCal.g:6505:2: iv_ruleAnnotationArgument= ruleAnnotationArgument EOF
            {
             newCompositeNode(grammarAccess.getAnnotationArgumentRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAnnotationArgument=ruleAnnotationArgument();

            state._fsp--;

             current =iv_ruleAnnotationArgument; 
            match(input,EOF,FOLLOW_2); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnnotationArgument"


    // $ANTLR start "ruleAnnotationArgument"
    // InternalCal.g:6512:1: ruleAnnotationArgument returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleAnnotationArgument() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_value_2_0=null;

         enterRule(); 
            
        try {
            // InternalCal.g:6515:28: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? ) )
            // InternalCal.g:6516:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? )
            {
            // InternalCal.g:6516:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? )
            // InternalCal.g:6516:2: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )?
            {
            // InternalCal.g:6516:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalCal.g:6517:1: (lv_name_0_0= RULE_ID )
            {
            // InternalCal.g:6517:1: (lv_name_0_0= RULE_ID )
            // InternalCal.g:6518:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_31); 

            			newLeafNode(lv_name_0_0, grammarAccess.getAnnotationArgumentAccess().getNameIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAnnotationArgumentRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"org.eclipse.xtext.common.Terminals.ID");
            	    

            }


            }

            // InternalCal.g:6534:2: (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )?
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==24) ) {
                alt124=1;
            }
            switch (alt124) {
                case 1 :
                    // InternalCal.g:6534:4: otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,24,FOLLOW_92); 

                        	newLeafNode(otherlv_1, grammarAccess.getAnnotationArgumentAccess().getEqualsSignKeyword_1_0());
                        
                    // InternalCal.g:6538:1: ( (lv_value_2_0= RULE_STRING ) )
                    // InternalCal.g:6539:1: (lv_value_2_0= RULE_STRING )
                    {
                    // InternalCal.g:6539:1: (lv_value_2_0= RULE_STRING )
                    // InternalCal.g:6540:3: lv_value_2_0= RULE_STRING
                    {
                    lv_value_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			newLeafNode(lv_value_2_0, grammarAccess.getAnnotationArgumentAccess().getValueSTRINGTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getAnnotationArgumentRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"org.eclipse.xtext.common.Terminals.STRING");
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnnotationArgument"

    // Delegated rules


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA82 dfa82 = new DFA82(this);
    protected DFA103 dfa103 = new DFA103(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\27\1\uffff\1\4\3\uffff\1\31\1\4\1\30\1\11\1\4\1\35\1\32\1\30\1\11\1\32";
    static final String dfa_3s = "\1\136\1\uffff\1\4\3\uffff\1\136\1\4\1\33\1\11\1\4\1\136\2\33\1\11\1\33";
    static final String dfa_4s = "\1\uffff\1\4\1\uffff\1\1\1\2\1\3\12\uffff";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\5\uffff\1\3\2\uffff\1\4\63\uffff\5\5\1\uffff\1\5\1\uffff\2\5\1\2",
            "",
            "\1\6",
            "",
            "",
            "",
            "\1\7\3\uffff\1\3\2\uffff\1\4\63\uffff\5\5\1\uffff\1\5\1\uffff\2\5\1\2",
            "\1\10",
            "\1\11\1\uffff\1\12\1\13",
            "\1\14",
            "\1\15",
            "\1\3\2\uffff\1\4\63\uffff\5\5\1\uffff\1\5\1\uffff\2\5\1\2",
            "\1\12\1\13",
            "\1\16\1\uffff\1\12\1\13",
            "\1\17",
            "\1\12\1\13"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "()* loopback of 380:1: ( ( (lv_functions_2_0= ruleFunction ) ) | ( (lv_procedures_3_0= ruleAstProcedure ) ) | ( (lv_variables_4_0= ruleConstantVariable ) ) )*";
        }
    }
    static final String dfa_7s = "\27\uffff";
    static final String dfa_8s = "\1\4\1\uffff\1\4\2\uffff\1\24\4\uffff\2\4\1\54\1\4\1\24\1\30\1\11\2\4\1\32\1\30\1\11\1\32";
    static final String dfa_9s = "\1\136\1\uffff\1\4\2\uffff\1\26\4\uffff\1\136\1\4\1\57\1\4\1\26\1\33\1\11\1\4\1\136\2\33\1\11\1\33";
    static final String dfa_10s = "\1\uffff\1\7\1\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6\15\uffff";
    static final String dfa_11s = "\27\uffff}>";
    static final String[] dfa_12s = {
            "\1\5\22\uffff\1\1\5\uffff\1\3\2\uffff\1\4\2\uffff\2\1\6\uffff\1\11\1\6\2\uffff\1\7\44\uffff\5\10\1\uffff\1\10\1\uffff\2\10\1\2",
            "",
            "\1\12",
            "",
            "",
            "\1\13\1\uffff\1\14",
            "",
            "",
            "",
            "",
            "\1\5\24\uffff\1\15\3\uffff\1\3\2\uffff\1\4\13\uffff\1\6\2\uffff\1\7\44\uffff\5\10\1\uffff\1\10\1\uffff\2\10\1\2",
            "\1\16",
            "\1\6\2\uffff\1\7",
            "\1\17",
            "\1\13\1\uffff\1\14",
            "\1\20\1\uffff\1\21\1\22",
            "\1\23",
            "\1\24",
            "\1\5\30\uffff\1\3\2\uffff\1\4\13\uffff\1\6\2\uffff\1\7\44\uffff\5\10\1\uffff\1\10\1\uffff\2\10\1\2",
            "\1\21\1\22",
            "\1\25\1\uffff\1\21\1\22",
            "\1\26",
            "\1\21\1\22"
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "()* loopback of 670:1: ( ( (lv_functions_14_0= ruleFunction ) ) | ( (lv_procedures_15_0= ruleAstProcedure ) ) | ( (lv_actions_16_0= ruleAstAction ) ) | ( (lv_initializes_17_0= ruleInitialize ) ) | ( (lv_stateVariables_18_0= ruleStateVariable ) ) | ( (lv_localFsms_19_0= ruleLocalFsm ) ) )*";
        }
    }
    static final String dfa_13s = "\24\uffff";
    static final String dfa_14s = "\2\4\1\24\3\uffff\2\4\2\uffff\1\4\1\24\1\30\1\11\2\4\1\32\1\30\1\11\1\32";
    static final String dfa_15s = "\1\136\1\4\1\63\3\uffff\1\136\1\4\2\uffff\1\4\1\63\1\33\1\11\1\4\1\136\2\33\1\11\1\33";
    static final String dfa_16s = "\3\uffff\1\3\1\4\1\5\2\uffff\1\1\1\2\12\uffff";
    static final String dfa_17s = "\24\uffff}>";
    static final String[] dfa_18s = {
            "\1\2\57\uffff\1\3\2\uffff\1\4\3\uffff\1\5\42\uffff\1\1",
            "\1\6",
            "\1\7\4\uffff\1\11\26\uffff\1\10\2\uffff\1\10",
            "",
            "",
            "",
            "\1\2\24\uffff\1\12\32\uffff\1\3\2\uffff\1\4\3\uffff\1\5\42\uffff\1\1",
            "\1\13",
            "",
            "",
            "\1\14",
            "\1\7\4\uffff\1\11\26\uffff\1\10\2\uffff\1\10",
            "\1\15\1\uffff\1\16\1\17",
            "\1\20",
            "\1\21",
            "\1\2\57\uffff\1\3\2\uffff\1\4\3\uffff\1\5\42\uffff\1\1",
            "\1\16\1\17",
            "\1\22\1\uffff\1\16\1\17",
            "\1\23",
            "\1\16\1\17"
    };

    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final char[] dfa_14 = DFA.unpackEncodedStringToUnsignedChars(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[][] dfa_18 = unpackEncodedStringArray(dfa_18s);

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = dfa_13;
            this.eof = dfa_13;
            this.min = dfa_14;
            this.max = dfa_15;
            this.accept = dfa_16;
            this.special = dfa_17;
            this.transition = dfa_18;
        }
        public String getDescription() {
            return "3694:1: (this_StatementAssign_0= ruleStatementAssign | this_StatementCall_1= ruleStatementCall | this_StatementForeach_2= ruleStatementForeach | this_StatementIf_3= ruleStatementIf | this_StatementWhile_4= ruleStatementWhile )";
        }
    }
    static final String dfa_19s = "\13\uffff";
    static final String dfa_20s = "\3\uffff\1\10\6\uffff\1\10";
    static final String dfa_21s = "\1\4\2\uffff\1\20\3\uffff\1\4\2\uffff\1\20";
    static final String dfa_22s = "\1\136\2\uffff\1\115\3\uffff\1\4\2\uffff\1\115";
    static final String dfa_23s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\7\1\uffff\1\6\1\3\1\uffff";
    static final String dfa_24s = "\13\uffff}>";
    static final String[] dfa_25s = {
            "\1\3\5\5\17\uffff\1\6\26\uffff\1\1\6\uffff\1\4\32\uffff\2\5\12\uffff\1\2",
            "",
            "",
            "\1\10\3\uffff\1\7\1\uffff\3\10\1\2\3\10\2\uffff\1\10\1\uffff\2\10\5\uffff\2\10\3\uffff\2\10\1\uffff\1\11\1\10\4\uffff\1\10\1\uffff\3\10\1\uffff\22\10",
            "",
            "",
            "",
            "\1\12",
            "",
            "",
            "\1\10\3\uffff\1\7\1\uffff\3\10\1\2\3\10\2\uffff\1\10\1\uffff\2\10\5\uffff\2\10\3\uffff\2\10\1\uffff\1\11\1\10\4\uffff\1\10\1\uffff\3\10\1\uffff\22\10"
    };

    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[][] dfa_25 = unpackEncodedStringArray(dfa_25s);

    class DFA103 extends DFA {

        public DFA103(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 103;
            this.eot = dfa_19;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "4785:1: (this_ExpressionList_0= ruleExpressionList | this_ExpressionCall_1= ruleExpressionCall | this_ExpressionIndex_2= ruleExpressionIndex | this_ExpressionIf_3= ruleExpressionIf | this_ExpressionLiteral_4= ruleExpressionLiteral | this_ExpressionVariable_5= ruleExpressionVariable | (otherlv_6= '(' this_AstExpression_7= ruleAstExpression otherlv_8= ')' ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000000000E0000L,0x0000000040000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000060000L,0x0000000040000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000120800000L,0x0000000075F00000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00810000020003F0L,0x00000000400DC200L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000128000000L,0x0000000075F00000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000120000000L,0x0000000075F00000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000130000000L,0x0000000075F00000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000014000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000120400000L,0x0000000075F00000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000004400000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000981920800010L,0x0000000075F00000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000800800000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000020000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000080C00000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000100000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000280800000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000204000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0890000000800010L,0x0000000040000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000800010L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000002000010L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000002000012L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000060000000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000100000000010L,0x0000000040000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000600080800010L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000600084800000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000400080800000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000400004800000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000800000000010L,0x0000000040000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0002000004000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000010L,0x0000000040000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0009000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x008100000A0003F0L,0x00000000400DC200L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0010000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0080000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0E90000000800010L,0x0000000040000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0600000000800000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0890000000000012L,0x0000000040000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0800000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000001000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000400000002L,0x0000000000000038L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000002L,0x00000000000000C0L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000300L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000020000000002L,0x0000000000001C00L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0002000004400000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0008000001000002L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000200L});

}