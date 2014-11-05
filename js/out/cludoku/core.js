// Compiled by ClojureScript 0.0-2173
goog.provide('cludoku.core');
goog.require('cljs.core');
goog.require('cludoku.board');
goog.require('om.dom');
goog.require('om.dom');
goog.require('om.core');
goog.require('om.core');
goog.require('cludoku.solver');
goog.require('cludoku.solver');
goog.require('cludoku.board');
cljs.core.enable_console_print_BANG_.call(null);
cludoku.core.default_board = "3 3\n2 _ _ _ 8 _ 7 _ 1\n_ 5 _ _ _ _ _ _ _\n_ _ 6 _ _ _ 2 _ 4\n7 _ _ 1 2 _ _ _ _\n_ 2 _ 5 _ 6 _ 3 _\n_ _ _ _ 3 9 _ _ 8\n8 _ 2 _ _ _ 9 _ _\n_ _ _ _ _ _ _ 6 _\n3 _ 4 _ 1 _ _ _ 2";
cludoku.core.app_state = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
cludoku.core.reset_game = (function reset_game(board_name,board_str){return cljs.core.reset_BANG_.call(null,cludoku.core.app_state,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"name","name",1017277949),board_name,new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"board","board",1107812952),cludoku.board.create_board.call(null,cludoku.board.import_board.call(null,board_str)),new cljs.core.Keyword(null,"applied-rule","applied-rule",3746709950),"<Initial state>"], null)], null),new cljs.core.Keyword(null,"current-state","current-state",2168441519),0], null));
});
cludoku.core.reset_game.call(null,"*default*",cludoku.core.default_board);
cludoku.core.sudoku_cell_view = (function sudoku_cell_view(p__5700,owner){var map__5705 = p__5700;var map__5705__$1 = ((cljs.core.seq_QMARK_.call(null,map__5705))?cljs.core.apply.call(null,cljs.core.hash_map,map__5705):map__5705);var update = cljs.core.get.call(null,map__5705__$1,new cljs.core.Keyword(null,"update","update",4470025275));var cands = cljs.core.get.call(null,map__5705__$1,new cljs.core.Keyword(null,"cands","cands",1108331473));if(typeof cludoku.core.t5706 !== 'undefined')
{} else
{
/**
* @constructor
*/
cludoku.core.t5706 = (function (cands,update,map__5705,owner,p__5700,sudoku_cell_view,meta5707){
this.cands = cands;
this.update = update;
this.map__5705 = map__5705;
this.owner = owner;
this.p__5700 = p__5700;
this.sudoku_cell_view = sudoku_cell_view;
this.meta5707 = meta5707;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cludoku.core.t5706.cljs$lang$type = true;
cludoku.core.t5706.cljs$lang$ctorStr = "cludoku.core/t5706";
cludoku.core.t5706.cljs$lang$ctorPrWriter = (function (this__4033__auto__,writer__4034__auto__,opt__4035__auto__){return cljs.core._write.call(null,writer__4034__auto__,"cludoku.core/t5706");
});
cludoku.core.t5706.prototype.om$core$IRender$ = true;
cludoku.core.t5706.prototype.om$core$IRender$render$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;return cljs.core.apply.call(null,om.dom.div,{"className": "sudoku-cell"},((cljs.core._EQ_.call(null,1,cljs.core.count.call(null,self__.cands)))?cljs.core._conj.call(null,cljs.core.List.EMPTY,React.DOM.span({"className": "sudoku-number"},cljs.core.first.call(null,self__.cands))):cljs.core.map.call(null,(function (c){var extra_class = (cljs.core.truth_((function (){var and__3454__auto__ = self__.update;if(cljs.core.truth_(and__3454__auto__))
{return !(cljs.core.contains_QMARK_.call(null,self__.update,c));
} else
{return and__3454__auto__;
}
})())?" sudoku-candidate-dropped":"");return React.DOM.div({"className": [cljs.core.str("sudoku-candidate "),cljs.core.str("sudoku-candidate-"),cljs.core.str(c),cljs.core.str(extra_class)].join('')},c);
}),self__.cands)));
});
cludoku.core.t5706.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_5708){var self__ = this;
var _5708__$1 = this;return self__.meta5707;
});
cludoku.core.t5706.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_5708,meta5707__$1){var self__ = this;
var _5708__$1 = this;return (new cludoku.core.t5706(self__.cands,self__.update,self__.map__5705,self__.owner,self__.p__5700,self__.sudoku_cell_view,meta5707__$1));
});
cludoku.core.__GT_t5706 = (function __GT_t5706(cands__$1,update__$1,map__5705__$2,owner__$1,p__5700__$1,sudoku_cell_view__$1,meta5707){return (new cludoku.core.t5706(cands__$1,update__$1,map__5705__$2,owner__$1,p__5700__$1,sudoku_cell_view__$1,meta5707));
});
}
return (new cludoku.core.t5706(cands,update,map__5705__$1,owner,p__5700,sudoku_cell_view,null));
});
cludoku.core.sudoku_board_view = (function sudoku_board_view(app,owner){if(typeof cludoku.core.t5712 !== 'undefined')
{} else
{
/**
* @constructor
*/
cludoku.core.t5712 = (function (owner,app,sudoku_board_view,meta5713){
this.owner = owner;
this.app = app;
this.sudoku_board_view = sudoku_board_view;
this.meta5713 = meta5713;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cludoku.core.t5712.cljs$lang$type = true;
cludoku.core.t5712.cljs$lang$ctorStr = "cludoku.core/t5712";
cludoku.core.t5712.cljs$lang$ctorPrWriter = (function (this__4033__auto__,writer__4034__auto__,opt__4035__auto__){return cljs.core._write.call(null,writer__4034__auto__,"cludoku.core/t5712");
});
cludoku.core.t5712.prototype.om$core$IRender$ = true;
cludoku.core.t5712.prototype.om$core$IRender$render$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;var state = cljs.core.nth.call(null,new cljs.core.Keyword(null,"states","states",4416389492).cljs$core$IFn$_invoke$arity$1(self__.app),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app));var board = new cljs.core.Keyword(null,"board","board",1107812952).cljs$core$IFn$_invoke$arity$1(state);var updates = new cljs.core.Keyword(null,"applied-updates","applied-updates",1140660972).cljs$core$IFn$_invoke$arity$1(state);var cell_map = new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board);var board_range = cljs.core.range.call(null,cludoku.board.dim.call(null,board));return cljs.core.apply.call(null,om.dom.div,{"className": "sudoku-board"},cljs.core.map.call(null,(function (row_n){return cljs.core.apply.call(null,om.dom.div,{"className": "sudoku-row"},cljs.core.map.call(null,(function (col_n){var coords = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [row_n,col_n], null);return om.core.build.call(null,cludoku.core.sudoku_cell_view,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"cands","cands",1108331473),cljs.core.get.call(null,cell_map,coords),new cljs.core.Keyword(null,"update","update",4470025275),cljs.core.get.call(null,updates,coords)], null));
}),board_range));
}),board_range));
});
cludoku.core.t5712.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_5714){var self__ = this;
var _5714__$1 = this;return self__.meta5713;
});
cludoku.core.t5712.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_5714,meta5713__$1){var self__ = this;
var _5714__$1 = this;return (new cludoku.core.t5712(self__.owner,self__.app,self__.sudoku_board_view,meta5713__$1));
});
cludoku.core.__GT_t5712 = (function __GT_t5712(owner__$1,app__$1,sudoku_board_view__$1,meta5713){return (new cludoku.core.t5712(owner__$1,app__$1,sudoku_board_view__$1,meta5713));
});
}
return (new cludoku.core.t5712(owner,app,sudoku_board_view,null));
});
cludoku.core.prev_step = (function prev_step(app){if((new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(app) > 0))
{return cljs.core.update_in.call(null,app,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"current-state","current-state",2168441519)], null),cljs.core.dec);
} else
{return app;
}
});
cludoku.core.next_step = (function next_step(app){var states = new cljs.core.Keyword(null,"states","states",4416389492).cljs$core$IFn$_invoke$arity$1(app);var current_state = new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(app);var last_state = (cljs.core.count.call(null,states) - 1);var current_board = new cljs.core.Keyword(null,"board","board",1107812952).cljs$core$IFn$_invoke$arity$1(cljs.core.get.call(null,states,current_state));if((current_state < last_state))
{return cljs.core.update_in.call(null,app,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"current-state","current-state",2168441519)], null),cljs.core.inc);
} else
{if(cljs.core.truth_(new cljs.core.Keyword(null,"finished?","finished?",1605255551).cljs$core$IFn$_invoke$arity$1(app)))
{return app;
} else
{var pending_rules = cludoku.solver.rules;while(true){
var map__5718 = cljs.core.first.call(null,pending_rules);var map__5718__$1 = ((cljs.core.seq_QMARK_.call(null,map__5718))?cljs.core.apply.call(null,cljs.core.hash_map,map__5718):map__5718);var rule_name = cljs.core.get.call(null,map__5718__$1,new cljs.core.Keyword(null,"name","name",1017277949));var rule_function = cljs.core.get.call(null,map__5718__$1,new cljs.core.Keyword(null,"function","function",2394842954));var update = rule_function.call(null,current_board);var updated_board = cludoku.board.update_board.call(null,current_board,update);if(cljs.core.not_EQ_.call(null,current_board,updated_board))
{return cljs.core.update_in.call(null,cljs.core.update_in.call(null,cljs.core.update_in.call(null,app,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"current-state","current-state",2168441519)], null),cljs.core.inc),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492)], null),((function (pending_rules,map__5718,map__5718__$1,rule_name,rule_function,update,updated_board){
return (function (p1__5715_SHARP_){return cljs.core.conj.call(null,p1__5715_SHARP_,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"board","board",1107812952),current_board,new cljs.core.Keyword(null,"applied-updates","applied-updates",1140660972),update,new cljs.core.Keyword(null,"applied-rule","applied-rule",3746709950),rule_name], null));
});})(pending_rules,map__5718,map__5718__$1,rule_name,rule_function,update,updated_board))
),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492)], null),((function (pending_rules,map__5718,map__5718__$1,rule_name,rule_function,update,updated_board){
return (function (p1__5716_SHARP_){return cljs.core.conj.call(null,p1__5716_SHARP_,new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"board","board",1107812952),updated_board,new cljs.core.Keyword(null,"applied-updates","applied-updates",1140660972),cljs.core.PersistentHashSet.EMPTY,new cljs.core.Keyword(null,"applied-rule","applied-rule",3746709950),"Cleanup",new cljs.core.Keyword(null,"solved?","solved?",3278804044),cludoku.board.solved_QMARK_.call(null,updated_board),new cljs.core.Keyword(null,"finished?","finished?",1605255551),cludoku.board.solved_QMARK_.call(null,updated_board)], null));
});})(pending_rules,map__5718,map__5718__$1,rule_name,rule_function,update,updated_board))
);
} else
{if((cljs.core.count.call(null,pending_rules) > 1))
{{
var G__5719 = cljs.core.rest.call(null,pending_rules);
pending_rules = G__5719;
continue;
}
} else
{return cljs.core.assoc_in.call(null,cljs.core.assoc_in.call(null,app,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(app),new cljs.core.Keyword(null,"finished?","finished?",1605255551)], null),true),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(app),new cljs.core.Keyword(null,"solved?","solved?",3278804044)], null),cludoku.board.solved_QMARK_.call(null,current_board));
}
}
break;
}
}
}
});
cludoku.core.sudoku_controls_view = (function sudoku_controls_view(app,owner){if(typeof cludoku.core.t5723 !== 'undefined')
{} else
{
/**
* @constructor
*/
cludoku.core.t5723 = (function (owner,app,sudoku_controls_view,meta5724){
this.owner = owner;
this.app = app;
this.sudoku_controls_view = sudoku_controls_view;
this.meta5724 = meta5724;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cludoku.core.t5723.cljs$lang$type = true;
cludoku.core.t5723.cljs$lang$ctorStr = "cludoku.core/t5723";
cludoku.core.t5723.cljs$lang$ctorPrWriter = (function (this__4033__auto__,writer__4034__auto__,opt__4035__auto__){return cljs.core._write.call(null,writer__4034__auto__,"cludoku.core/t5723");
});
cludoku.core.t5723.prototype.om$core$IRender$ = true;
cludoku.core.t5723.prototype.om$core$IRender$render$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return React.DOM.div(null,React.DOM.button({"disabled": (new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app) === 0), "onClick": (function (e){return om.core.transact_BANG_.call(null,self__.app,cludoku.core.prev_step);
}), "accessKey": "p"},"Previous step"),React.DOM.button({"disabled": (cljs.core.truth_(cljs.core.get_in.call(null,self__.app,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app),new cljs.core.Keyword(null,"finished?","finished?",1605255551)], null)))?"disabled":null), "onClick": (function (e){return om.core.transact_BANG_.call(null,self__.app,cludoku.core.next_step);
}), "accessKey": "n"},"Next step"));
});
cludoku.core.t5723.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_5725){var self__ = this;
var _5725__$1 = this;return self__.meta5724;
});
cludoku.core.t5723.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_5725,meta5724__$1){var self__ = this;
var _5725__$1 = this;return (new cludoku.core.t5723(self__.owner,self__.app,self__.sudoku_controls_view,meta5724__$1));
});
cludoku.core.__GT_t5723 = (function __GT_t5723(owner__$1,app__$1,sudoku_controls_view__$1,meta5724){return (new cludoku.core.t5723(owner__$1,app__$1,sudoku_controls_view__$1,meta5724));
});
}
return (new cludoku.core.t5723(owner,app,sudoku_controls_view,null));
});
cludoku.core.sudoku_info_view = (function sudoku_info_view(app,owner){if(typeof cludoku.core.t5729 !== 'undefined')
{} else
{
/**
* @constructor
*/
cludoku.core.t5729 = (function (owner,app,sudoku_info_view,meta5730){
this.owner = owner;
this.app = app;
this.sudoku_info_view = sudoku_info_view;
this.meta5730 = meta5730;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cludoku.core.t5729.cljs$lang$type = true;
cludoku.core.t5729.cljs$lang$ctorStr = "cludoku.core/t5729";
cludoku.core.t5729.cljs$lang$ctorPrWriter = (function (this__4033__auto__,writer__4034__auto__,opt__4035__auto__){return cljs.core._write.call(null,writer__4034__auto__,"cludoku.core/t5729");
});
cludoku.core.t5729.prototype.om$core$IRender$ = true;
cludoku.core.t5729.prototype.om$core$IRender$render$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var board_name = new cljs.core.Keyword(null,"name","name",1017277949).cljs$core$IFn$_invoke$arity$1(self__.app);var current_state = new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app);var last_state = (cljs.core.count.call(null,new cljs.core.Keyword(null,"states","states",4416389492).cljs$core$IFn$_invoke$arity$1(self__.app)) - 1);var current_rule = cljs.core.get_in.call(null,self__.app,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),current_state,new cljs.core.Keyword(null,"applied-rule","applied-rule",3746709950)], null));return React.DOM.div(null,React.DOM.h2({"className": "board-name"},board_name),React.DOM.span({"className": "last-change"},(cljs.core.truth_(cljs.core.get_in.call(null,self__.app,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app),new cljs.core.Keyword(null,"solved?","solved?",3278804044)], null)))?"Solved!":(cljs.core.truth_(cljs.core.get_in.call(null,self__.app,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"states","states",4416389492),new cljs.core.Keyword(null,"current-state","current-state",2168441519).cljs$core$IFn$_invoke$arity$1(self__.app),new cljs.core.Keyword(null,"finished?","finished?",1605255551)], null)))?"Could not solve sudoku.":((cljs.core._EQ_.call(null,current_state,0))?"<Initial state>":((new cljs.core.Keyword(null,"else","else",1017020587))?[cljs.core.str("Applied \""),cljs.core.str(current_rule),cljs.core.str("\".")].join(''):null))))));
});
cludoku.core.t5729.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_5731){var self__ = this;
var _5731__$1 = this;return self__.meta5730;
});
cludoku.core.t5729.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_5731,meta5730__$1){var self__ = this;
var _5731__$1 = this;return (new cludoku.core.t5729(self__.owner,self__.app,self__.sudoku_info_view,meta5730__$1));
});
cludoku.core.__GT_t5729 = (function __GT_t5729(owner__$1,app__$1,sudoku_info_view__$1,meta5730){return (new cludoku.core.t5729(owner__$1,app__$1,sudoku_info_view__$1,meta5730));
});
}
return (new cludoku.core.t5729(owner,app,sudoku_info_view,null));
});
om.core.root.call(null,cludoku.core.sudoku_board_view,cludoku.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",4427965699),document.getElementById("sudoku-board")], null));
om.core.root.call(null,cludoku.core.sudoku_controls_view,cludoku.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",4427965699),document.getElementById("sudoku-controls")], null));
om.core.root.call(null,cludoku.core.sudoku_info_view,cludoku.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",4427965699),document.getElementById("sudoku-info")], null));
