// Compiled by ClojureScript 0.0-2173
goog.provide('cludoku.board');
goog.require('cljs.core');
goog.require('cljs.reader');
goog.require('clojure.string');
goog.require('cljs.reader');

/**
* @constructor
* @param {*} block_width
* @param {*} block_height
* @param {*} cells
* @param {*} __meta
* @param {*} __extmap
* @param {*=} __meta 
* @param {*=} __extmap
*/
cludoku.board.Board = (function (block_width,block_height,cells,__meta,__extmap){
this.block_width = block_width;
this.block_height = block_height;
this.cells = cells;
this.__meta = __meta;
this.__extmap = __extmap;
this.cljs$lang$protocol_mask$partition0$ = 2229667594;
this.cljs$lang$protocol_mask$partition1$ = 8192;
if(arguments.length>3){
this.__meta = __meta;
this.__extmap = __extmap;
} else {
this.__meta=null;
this.__extmap=null;
}
})
cludoku.board.Board.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4024__auto__){var self__ = this;
var this__4024__auto____$1 = this;var h__3854__auto__ = self__.__hash;if(!((h__3854__auto__ == null)))
{return h__3854__auto__;
} else
{var h__3854__auto____$1 = cljs.core.hash_imap.call(null,this__4024__auto____$1);self__.__hash = h__3854__auto____$1;
return h__3854__auto____$1;
}
});
cludoku.board.Board.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4029__auto__,k__4030__auto__){var self__ = this;
var this__4029__auto____$1 = this;return cljs.core._lookup.call(null,this__4029__auto____$1,k__4030__auto__,null);
});
cludoku.board.Board.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4031__auto__,k5982,else__4032__auto__){var self__ = this;
var this__4031__auto____$1 = this;if(cljs.core.keyword_identical_QMARK_.call(null,k5982,new cljs.core.Keyword(null,"block-width","block-width",813341688)))
{return self__.block_width;
} else
{if(cljs.core.keyword_identical_QMARK_.call(null,k5982,new cljs.core.Keyword(null,"block-height","block-height",2953404889)))
{return self__.block_height;
} else
{if(cljs.core.keyword_identical_QMARK_.call(null,k5982,new cljs.core.Keyword(null,"cells","cells",1108448963)))
{return self__.cells;
} else
{if(new cljs.core.Keyword(null,"else","else",1017020587))
{return cljs.core.get.call(null,self__.__extmap,k5982,else__4032__auto__);
} else
{return null;
}
}
}
}
});
cludoku.board.Board.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4036__auto__,k__4037__auto__,G__5981){var self__ = this;
var this__4036__auto____$1 = this;var pred__5984 = cljs.core.keyword_identical_QMARK_;var expr__5985 = k__4037__auto__;if(cljs.core.truth_(pred__5984.call(null,new cljs.core.Keyword(null,"block-width","block-width",813341688),expr__5985)))
{return (new cludoku.board.Board(G__5981,self__.block_height,self__.cells,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_(pred__5984.call(null,new cljs.core.Keyword(null,"block-height","block-height",2953404889),expr__5985)))
{return (new cludoku.board.Board(self__.block_width,G__5981,self__.cells,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_(pred__5984.call(null,new cljs.core.Keyword(null,"cells","cells",1108448963),expr__5985)))
{return (new cludoku.board.Board(self__.block_width,self__.block_height,G__5981,self__.__meta,self__.__extmap,null));
} else
{return (new cludoku.board.Board(self__.block_width,self__.block_height,self__.cells,self__.__meta,cljs.core.assoc.call(null,self__.__extmap,k__4037__auto__,G__5981),null));
}
}
}
});
cludoku.board.Board.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4043__auto__,writer__4044__auto__,opts__4045__auto__){var self__ = this;
var this__4043__auto____$1 = this;var pr_pair__4046__auto__ = (function (keyval__4047__auto__){return cljs.core.pr_sequential_writer.call(null,writer__4044__auto__,cljs.core.pr_writer,""," ","",opts__4045__auto__,keyval__4047__auto__);
});return cljs.core.pr_sequential_writer.call(null,writer__4044__auto__,pr_pair__4046__auto__,"#cludoku.board.Board{",", ","}",opts__4045__auto__,cljs.core.concat.call(null,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"block-width","block-width",813341688),self__.block_width],null)),(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"block-height","block-height",2953404889),self__.block_height],null)),(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"cells","cells",1108448963),self__.cells],null))], null),self__.__extmap));
});
cludoku.board.Board.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4034__auto__,entry__4035__auto__){var self__ = this;
var this__4034__auto____$1 = this;if(cljs.core.vector_QMARK_.call(null,entry__4035__auto__))
{return cljs.core._assoc.call(null,this__4034__auto____$1,cljs.core._nth.call(null,entry__4035__auto__,0),cljs.core._nth.call(null,entry__4035__auto__,1));
} else
{return cljs.core.reduce.call(null,cljs.core._conj,this__4034__auto____$1,entry__4035__auto__);
}
});
cludoku.board.Board.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4041__auto__){var self__ = this;
var this__4041__auto____$1 = this;return cljs.core.seq.call(null,cljs.core.concat.call(null,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"block-width","block-width",813341688),self__.block_width],null)),(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"block-height","block-height",2953404889),self__.block_height],null)),(new cljs.core.PersistentVector(null,2,5,cljs.core.PersistentVector.EMPTY_NODE,[new cljs.core.Keyword(null,"cells","cells",1108448963),self__.cells],null))], null),self__.__extmap));
});
cludoku.board.Board.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4033__auto__){var self__ = this;
var this__4033__auto____$1 = this;return (3 + cljs.core.count.call(null,self__.__extmap));
});
cludoku.board.Board.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this__4025__auto__,other__4026__auto__){var self__ = this;
var this__4025__auto____$1 = this;if(cljs.core.truth_((function (){var and__3431__auto__ = other__4026__auto__;if(cljs.core.truth_(and__3431__auto__))
{return ((this__4025__auto____$1.constructor === other__4026__auto__.constructor)) && (cljs.core.equiv_map.call(null,this__4025__auto____$1,other__4026__auto__));
} else
{return and__3431__auto__;
}
})()))
{return true;
} else
{return false;
}
});
cludoku.board.Board.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4028__auto__,G__5981){var self__ = this;
var this__4028__auto____$1 = this;return (new cludoku.board.Board(self__.block_width,self__.block_height,self__.cells,G__5981,self__.__extmap,self__.__hash));
});
cludoku.board.Board.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4023__auto__){var self__ = this;
var this__4023__auto____$1 = this;return (new cludoku.board.Board(self__.block_width,self__.block_height,self__.cells,self__.__meta,self__.__extmap,self__.__hash));
});
cludoku.board.Board.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4027__auto__){var self__ = this;
var this__4027__auto____$1 = this;return self__.__meta;
});
cludoku.board.Board.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4038__auto__,k__4039__auto__){var self__ = this;
var this__4038__auto____$1 = this;if(cljs.core.contains_QMARK_.call(null,new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"block-height","block-height",2953404889),null,new cljs.core.Keyword(null,"cells","cells",1108448963),null,new cljs.core.Keyword(null,"block-width","block-width",813341688),null], null), null),k__4039__auto__))
{return cljs.core.dissoc.call(null,cljs.core.with_meta.call(null,cljs.core.into.call(null,cljs.core.PersistentArrayMap.EMPTY,this__4038__auto____$1),self__.__meta),k__4039__auto__);
} else
{return (new cludoku.board.Board(self__.block_width,self__.block_height,self__.cells,self__.__meta,cljs.core.not_empty.call(null,cljs.core.dissoc.call(null,self__.__extmap,k__4039__auto__)),null));
}
});
cludoku.board.Board.cljs$lang$type = true;
cludoku.board.Board.cljs$lang$ctorPrSeq = (function (this__4063__auto__){return cljs.core._conj.call(null,cljs.core.List.EMPTY,"cludoku.board/Board");
});
cludoku.board.Board.cljs$lang$ctorPrWriter = (function (this__4063__auto__,writer__4064__auto__){return cljs.core._write.call(null,writer__4064__auto__,"cludoku.board/Board");
});
cludoku.board.__GT_Board = (function __GT_Board(block_width,block_height,cells){return (new cludoku.board.Board(block_width,block_height,cells));
});
cludoku.board.map__GT_Board = (function map__GT_Board(G__5983){return (new cludoku.board.Board(new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(G__5983),new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(G__5983),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(G__5983),null,cljs.core.dissoc.call(null,G__5983,new cljs.core.Keyword(null,"block-width","block-width",813341688),new cljs.core.Keyword(null,"block-height","block-height",2953404889),new cljs.core.Keyword(null,"cells","cells",1108448963))));
});
cludoku.board.dim = (function dim(board){return (new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board) * new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(board));
});
cludoku.board.board_row = (function board_row(board,row_number){return cljs.core.merge.call(null,cljs.core.PersistentArrayMap.EMPTY,cljs.core.filter.call(null,(function (p1__5987_SHARP_){return cljs.core._EQ_.call(null,cljs.core.ffirst.call(null,p1__5987_SHARP_),row_number);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board)));
});
cludoku.board.board_col = (function board_col(board,col_number){return cljs.core.merge.call(null,cljs.core.PersistentArrayMap.EMPTY,cljs.core.filter.call(null,(function (p1__5988_SHARP_){return cljs.core._EQ_.call(null,cljs.core.second.call(null,cljs.core.first.call(null,p1__5988_SHARP_)),col_number);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board)));
});
cludoku.board.board_block = (function board_block(board,block_number){var first_row = (cljs.core.quot.call(null,block_number,new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board)) * new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board));var last_row = ((first_row + new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board)) - 1);var first_col = cljs.core.mod.call(null,(block_number * new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(board)),cludoku.board.dim.call(null,board));var last_col = ((first_col + new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(board)) - 1);return cljs.core.merge.call(null,cljs.core.PersistentArrayMap.EMPTY,cljs.core.filter.call(null,(function (p1__5989_SHARP_){return ((cljs.core.ffirst.call(null,p1__5989_SHARP_) >= first_row)) && ((cljs.core.ffirst.call(null,p1__5989_SHARP_) <= last_row)) && ((cljs.core.second.call(null,cljs.core.first.call(null,p1__5989_SHARP_)) >= first_col)) && ((cljs.core.second.call(null,cljs.core.first.call(null,p1__5989_SHARP_)) <= last_col));
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board)));
});
cludoku.board.block_for_cell = (function block_for_cell(board,p__5990){var vec__5992 = p__5990;var row_n = cljs.core.nth.call(null,vec__5992,0,null);var col_n = cljs.core.nth.call(null,vec__5992,1,null);var block_row = cljs.core.quot.call(null,row_n,new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board));var block_col = cljs.core.quot.call(null,col_n,new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(board));var block_number = ((new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board) * block_row) + block_col);return cludoku.board.board_block.call(null,board,block_number);
});
cludoku.board.cell_set_diff = (function cell_set_diff(cell_set1,cell_set2){return cljs.core.reduce.call(null,(function (acc,coord){var cands1 = cljs.core.get.call(null,cell_set1,coord);var cands2 = cljs.core.get.call(null,cell_set2,coord);if(cljs.core.not_EQ_.call(null,cands1,cands2))
{return cljs.core.into.call(null,acc,new cljs.core.PersistentArrayMap.fromArray([coord,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cands1,cands2], null)], true, false));
} else
{return acc;
}
}),cljs.core.PersistentArrayMap.EMPTY,cljs.core.keys.call(null,cell_set1));
});
cludoku.board.well_formed_QMARK_ = (function well_formed_QMARK_(proto_board){var dimensions = (new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(proto_board) * new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(proto_board));return (cljs.core.every_QMARK_.call(null,(function (p1__5993_SHARP_){return cljs.core._EQ_.call(null,cljs.core.count.call(null,p1__5993_SHARP_),dimensions);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(proto_board))) && (cljs.core._EQ_.call(null,cljs.core.count.call(null,new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(proto_board)),dimensions)) && (cljs.core.every_QMARK_.call(null,(function (x){return cljs.core.every_QMARK_.call(null,(function (p1__5994_SHARP_){return ((p1__5994_SHARP_ == null)) || (((p1__5994_SHARP_ >= 1)) && ((p1__5994_SHARP_ <= dimensions)));
}),x);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(proto_board)));
});
cludoku.board.remove_candidate = (function remove_candidate(number,cell_set){var result = cljs.core.reduce.call(null,(function (update,p__5998){var vec__5999 = p__5998;var cell_pos = cljs.core.nth.call(null,vec__5999,0,null);var cell_cands = cljs.core.nth.call(null,vec__5999,1,null);return cljs.core.into.call(null,update,((((cljs.core.count.call(null,cell_cands) > 1)) && (cljs.core.contains_QMARK_.call(null,cell_cands,number)))?new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cell_pos,cljs.core.set.call(null,cljs.core.remove.call(null,((function (vec__5999,cell_pos,cell_cands){
return (function (p1__5995_SHARP_){return cljs.core._EQ_.call(null,number,p1__5995_SHARP_);
});})(vec__5999,cell_pos,cell_cands))
,cell_cands))], null)], null):null));
}),cljs.core.PersistentArrayMap.EMPTY,cell_set);return result;
});
cludoku.board.remove_final_numbers = (function remove_final_numbers(board,new_final_numbers){var update = cljs.core.reduce.call(null,(function (acc_cells,final_number){var vec__6003 = final_number;var pos = cljs.core.nth.call(null,vec__6003,0,null);var one_element_set = cljs.core.nth.call(null,vec__6003,1,null);var number = cljs.core.first.call(null,one_element_set);var acc_board = cljs.core.update_in.call(null,board,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"cells","cells",1108448963)], null),cljs.core.merge,acc_cells);var row_wo_final = cludoku.board.remove_candidate.call(null,number,cludoku.board.board_row.call(null,acc_board,cljs.core.first.call(null,pos)));var col_wo_final = cludoku.board.remove_candidate.call(null,number,cludoku.board.board_col.call(null,acc_board,cljs.core.second.call(null,pos)));var block_wo_final = cludoku.board.remove_candidate.call(null,number,cludoku.board.block_for_cell.call(null,acc_board,pos));return cljs.core.merge.call(null,acc_cells,row_wo_final,col_wo_final,block_wo_final);
}),cljs.core.PersistentArrayMap.EMPTY,new_final_numbers);var updated_board = cljs.core.update_in.call(null,board,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"cells","cells",1108448963)], null),cljs.core.merge,update);var new_finals = cljs.core.filter.call(null,((function (update,updated_board){
return (function (p__6004){var vec__6005 = p__6004;var _ = cljs.core.nth.call(null,vec__6005,0,null);var new_cand_set = cljs.core.nth.call(null,vec__6005,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,new_cand_set),1);
});})(update,updated_board))
,update);if((cljs.core.count.call(null,new_finals) > 0))
{return remove_final_numbers.call(null,updated_board,new_finals);
} else
{return updated_board;
}
});
cludoku.board.create_board = (function create_board(proto_board){if(cludoku.board.well_formed_QMARK_.call(null,proto_board))
{var range_dim = cljs.core.range.call(null,(new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(proto_board) * new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(proto_board)));var proto_cells = new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(proto_board);var final_cells = cljs.core.reduce.call(null,((function (range_dim,proto_cells){
return (function (row_acc,x){return cljs.core.merge.call(null,row_acc,cljs.core.reduce.call(null,((function (range_dim,proto_cells){
return (function (col_acc,y){var row_x = cljs.core.nth.call(null,proto_cells,x);var cell_contents = cljs.core.nth.call(null,row_x,y);var cands = (((cell_contents == null))?cljs.core.set.call(null,cljs.core.map.call(null,cljs.core.inc,range_dim)):cljs.core.PersistentHashSet.fromArray([cell_contents], true));return cljs.core.merge.call(null,col_acc,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x,y], null),cands], null));
});})(range_dim,proto_cells))
,cljs.core.PersistentArrayMap.EMPTY,range_dim));
});})(range_dim,proto_cells))
,cljs.core.PersistentArrayMap.EMPTY,range_dim);var raw_board = cludoku.board.map__GT_Board.call(null,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"block-width","block-width",813341688),new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(proto_board),new cljs.core.Keyword(null,"block-height","block-height",2953404889),new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(proto_board),new cljs.core.Keyword(null,"cells","cells",1108448963),final_cells], null));return cludoku.board.remove_final_numbers.call(null,raw_board,cljs.core.filter.call(null,(function (p__6008){var vec__6009 = p__6008;var pos = cljs.core.nth.call(null,vec__6009,0,null);var cands = cljs.core.nth.call(null,vec__6009,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,cands),1);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(raw_board)));
} else
{throw Error("Inconsistent board cells!");
}
});
cludoku.board.consistent_sets_QMARK_ = (function consistent_sets_QMARK_(board,set_function){return cljs.core.every_QMARK_.call(null,(function (n){var final_numbers = cljs.core.map.call(null,cljs.core.first,cljs.core.filter.call(null,(function (p1__6010_SHARP_){return cljs.core._EQ_.call(null,cljs.core.count.call(null,p1__6010_SHARP_),1);
}),cljs.core.map.call(null,cljs.core.second,set_function.call(null,board,n))));var or__3443__auto__ = cljs.core.empty_QMARK_.call(null,final_numbers);if(or__3443__auto__)
{return or__3443__auto__;
} else
{return cljs.core.apply.call(null,cljs.core.distinct_QMARK_,final_numbers);
}
}),cljs.core.range.call(null,cludoku.board.dim.call(null,board)));
});
cludoku.board.consistent_QMARK_ = (function consistent_QMARK_(board){return (cludoku.board.consistent_sets_QMARK_.call(null,board,cludoku.board.board_row)) && (cludoku.board.consistent_sets_QMARK_.call(null,board,cludoku.board.board_col)) && (cludoku.board.consistent_sets_QMARK_.call(null,board,cludoku.board.board_block));
});
cludoku.board.update_board = (function update_board(board,update){var final_numbers = cljs.core.filter.call(null,(function (p1__6011_SHARP_){return cljs.core._EQ_.call(null,cljs.core.count.call(null,cljs.core.second.call(null,p1__6011_SHARP_)),1);
}),update);var updated_board = cljs.core.update_in.call(null,board,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"cells","cells",1108448963)], null),cljs.core.merge,update);return cludoku.board.remove_final_numbers.call(null,updated_board,final_numbers);
});
cludoku.board.solved_QMARK_ = (function solved_QMARK_(board){if(cludoku.board.consistent_QMARK_.call(null,board))
{var number_unknowns = cljs.core.count.call(null,cljs.core.filter.call(null,(function (coord_cand){return (cljs.core.count.call(null,cljs.core.second.call(null,coord_cand)) > 1);
}),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board)));return (number_unknowns === 0);
} else
{throw Error("Inconsistent board!");
}
});
cludoku.board.import_board = (function import_board(board_string){var lines = clojure.string.split_lines.call(null,board_string);var dimesions_spec = cljs.core.map.call(null,cljs.reader.read_string,clojure.string.split.call(null,cljs.core.first.call(null,lines),/ /));var rows_spec = cljs.core.rest.call(null,lines);return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"block-width","block-width",813341688),cljs.core.first.call(null,dimesions_spec),new cljs.core.Keyword(null,"block-height","block-height",2953404889),cljs.core.second.call(null,dimesions_spec),new cljs.core.Keyword(null,"cells","cells",1108448963),cljs.core.mapv.call(null,(function (line){return cljs.core.mapv.call(null,(function (cell){var number = cljs.reader.read_string.call(null,cell);if(typeof number === 'number')
{return number;
} else
{return null;
}
}),clojure.string.split.call(null,line,/ /));
}),rows_spec)], null);
});
cludoku.board.export_board = (function export_board(board){var index_range = cljs.core.range.call(null,(new cljs.core.Keyword(null,"block-height","block-height",2953404889).cljs$core$IFn$_invoke$arity$1(board) * new cljs.core.Keyword(null,"block-width","block-width",813341688).cljs$core$IFn$_invoke$arity$1(board)));return cljs.core.merge.call(null,board,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cells","cells",1108448963),cljs.core.vec.call(null,cljs.core.map.call(null,(function (nrow){return cljs.core.vec.call(null,cljs.core.map.call(null,(function (ncol){var cell_contents = cljs.core.second.call(null,cljs.core.find.call(null,new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [nrow,ncol], null)));if(cljs.core._EQ_.call(null,cljs.core.count.call(null,cell_contents),1))
{return cljs.core.first.call(null,cell_contents);
} else
{return null;
}
}),index_range));
}),index_range))], null));
});
