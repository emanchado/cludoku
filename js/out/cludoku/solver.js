// Compiled by ClojureScript 0.0-2173
goog.provide('cludoku.solver');
goog.require('cljs.core');
goog.require('cludoku.board');
goog.require('cludoku.board');
goog.require('clojure.set');
goog.require('clojure.set');
cludoku.solver.skip_cells = (function skip_cells(to_skip,cell_list){return cljs.core.into.call(null,cljs.core.PersistentArrayMap.EMPTY,cljs.core.remove.call(null,(function (p__6015){var vec__6016 = p__6015;var coords = cljs.core.nth.call(null,vec__6016,0,null);var cands = cljs.core.nth.call(null,vec__6016,1,null);return cljs.core.some.call(null,(function (p1__6012_SHARP_){return cljs.core._EQ_.call(null,coords,p1__6012_SHARP_);
}),to_skip);
}),cell_list));
});
cludoku.solver.with_candidate = (function with_candidate(cand){return (function (p__6019){var vec__6020 = p__6019;var _ = cljs.core.nth.call(null,vec__6020,0,null);var cands = cljs.core.nth.call(null,vec__6020,1,null);return cljs.core.contains_QMARK_.call(null,cands,cand);
});
});
cludoku.solver.drop_candidates = (function drop_candidates(unwanted_cands,cells){return cljs.core.into.call(null,cljs.core.PersistentArrayMap.EMPTY,cljs.core.vec.call(null,cljs.core.map.call(null,(function (p__6026){var vec__6027 = p__6026;var pos = cljs.core.nth.call(null,vec__6027,0,null);var cands = cljs.core.nth.call(null,vec__6027,1,null);return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [pos,cljs.core.set.call(null,cljs.core.remove.call(null,unwanted_cands,cands))], null);
}),cljs.core.filter.call(null,(function (p__6028){var vec__6029 = p__6028;var _ = cljs.core.nth.call(null,vec__6029,0,null);var cands = cljs.core.nth.call(null,vec__6029,1,null);return cljs.core.some.call(null,(function (p1__6021_SHARP_){return cljs.core.contains_QMARK_.call(null,cands,p1__6021_SHARP_);
}),unwanted_cands);
}),cells))));
});
cludoku.solver.unsolved_cells = (function unsolved_cells(cell_set){return cljs.core.filter.call(null,(function (p__6032){var vec__6033 = p__6032;var _ = cljs.core.nth.call(null,vec__6033,0,null);var cands = cljs.core.nth.call(null,vec__6033,1,null);return cljs.core.not_EQ_.call(null,cljs.core.count.call(null,cands),1);
}),cell_set);
});
cludoku.solver.naked_pairs = (function naked_pairs(cell_set){var cells_with_two_cands = cljs.core.group_by.call(null,cljs.core.count,cljs.core.vals.call(null,cell_set)).call(null,2);var repeated_pair = cljs.core.ffirst.call(null,cljs.core.filter.call(null,((function (cells_with_two_cands){
return (function (p1__6034_SHARP_){return cljs.core._EQ_.call(null,cljs.core.nth.call(null,p1__6034_SHARP_,1),2);
});})(cells_with_two_cands))
,cljs.core.frequencies.call(null,cells_with_two_cands)));if(cljs.core.truth_(repeated_pair))
{return cludoku.solver.drop_candidates.call(null,repeated_pair,cljs.core.filter.call(null,(function (p__6037){var vec__6038 = p__6037;var _ = cljs.core.nth.call(null,vec__6038,0,null);var cands = cljs.core.nth.call(null,vec__6038,1,null);return cljs.core.not_EQ_.call(null,cands,repeated_pair);
}),cell_set));
} else
{return cljs.core.PersistentArrayMap.EMPTY;
}
});
cludoku.solver.single_cell_candidate = (function single_cell_candidate(cell_set){var unsolved_cells = cludoku.solver.unsolved_cells.call(null,cell_set);var unsolved_cand_pos = cljs.core.reduce.call(null,((function (unsolved_cells){
return (function (acc,p__6043){var vec__6044 = p__6043;var pos = cljs.core.nth.call(null,vec__6044,0,null);var cands = cljs.core.nth.call(null,vec__6044,1,null);return cljs.core.conj.call(null,acc,cljs.core.reduce.call(null,((function (vec__6044,pos,cands,unsolved_cells){
return (function (acc2,cand){return cljs.core.conj.call(null,acc2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cand,cljs.core.conj.call(null,cljs.core.get.call(null,acc,cand,cljs.core.List.EMPTY),pos)], null));
});})(vec__6044,pos,cands,unsolved_cells))
,cljs.core.PersistentArrayMap.EMPTY,cands));
});})(unsolved_cells))
,cljs.core.PersistentArrayMap.EMPTY,unsolved_cells);return cljs.core.reduce.call(null,(function (acc,p__6045){var vec__6046 = p__6045;var cand = cljs.core.nth.call(null,vec__6046,0,null);var pos_list = cljs.core.nth.call(null,vec__6046,1,null);if(cljs.core._EQ_.call(null,cljs.core.count.call(null,pos_list),1))
{return cljs.core.conj.call(null,acc,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.first.call(null,pos_list),cljs.core.PersistentHashSet.fromArray([cand], true)], null));
} else
{return acc;
}
}),cljs.core.PersistentArrayMap.EMPTY,unsolved_cand_pos);
});
cludoku.solver.candidate_lines = (function candidate_lines(board){return cljs.core.reduce.call(null,(function (acc_changes,blockn){var block = cludoku.board.board_block.call(null,board,blockn);var unsolved_cells = cludoku.solver.unsolved_cells.call(null,block);var all_cands = cljs.core.reduce.call(null,((function (block,unsolved_cells){
return (function (acc,p__6057){var vec__6058 = p__6057;var _ = cljs.core.nth.call(null,vec__6058,0,null);var cands = cljs.core.nth.call(null,vec__6058,1,null);return clojure.set.union.call(null,acc,cands);
});})(block,unsolved_cells))
,cljs.core.PersistentHashSet.EMPTY,unsolved_cells);var cands_pos = cljs.core.reduce.call(null,((function (block,unsolved_cells,all_cands){
return (function (acc,cand){return cljs.core.merge.call(null,acc,new cljs.core.PersistentArrayMap.fromArray([cand,cljs.core.map.call(null,cljs.core.first,cljs.core.filter.call(null,cludoku.solver.with_candidate.call(null,cand),unsolved_cells))], true, false));
});})(block,unsolved_cells,all_cands))
,cljs.core.PersistentArrayMap.EMPTY,all_cands);var cand_horiz_lines = cljs.core.filter.call(null,((function (block,unsolved_cells,all_cands,cands_pos){
return (function (p__6059){var vec__6060 = p__6059;var cand = cljs.core.nth.call(null,vec__6060,0,null);var pos_list = cljs.core.nth.call(null,vec__6060,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,cljs.core.frequencies.call(null,cljs.core.map.call(null,cljs.core.first,pos_list))),1);
});})(block,unsolved_cells,all_cands,cands_pos))
,cands_pos);var cand_vert_lines = cljs.core.filter.call(null,((function (block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines){
return (function (p__6061){var vec__6062 = p__6061;var cand = cljs.core.nth.call(null,vec__6062,0,null);var pos_list = cljs.core.nth.call(null,vec__6062,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,cljs.core.frequencies.call(null,cljs.core.map.call(null,cljs.core.second,pos_list))),1);
});})(block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines))
,cands_pos);var horiz_line_updates = cljs.core.reduce.call(null,((function (block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines,cand_vert_lines){
return (function (acc,p__6063){var vec__6064 = p__6063;var cand = cljs.core.nth.call(null,vec__6064,0,null);var list_coords = cljs.core.nth.call(null,vec__6064,1,null);var rown = cljs.core.ffirst.call(null,list_coords);var row = cludoku.board.board_row.call(null,board,rown);return cljs.core.merge.call(null,acc,cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,list_coords,row)));
});})(block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines,cand_vert_lines))
,cljs.core.PersistentArrayMap.EMPTY,cand_horiz_lines);var vert_line_updates = cljs.core.reduce.call(null,((function (block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines,cand_vert_lines,horiz_line_updates){
return (function (acc,p__6065){var vec__6066 = p__6065;var cand = cljs.core.nth.call(null,vec__6066,0,null);var list_coords = cljs.core.nth.call(null,vec__6066,1,null);var coln = cljs.core.second.call(null,cljs.core.first.call(null,list_coords));var col = cludoku.board.board_col.call(null,board,coln);return cljs.core.merge.call(null,acc,cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,list_coords,col)));
});})(block,unsolved_cells,all_cands,cands_pos,cand_horiz_lines,cand_vert_lines,horiz_line_updates))
,cljs.core.PersistentArrayMap.EMPTY,cand_vert_lines);return cljs.core.merge.call(null,acc_changes,horiz_line_updates,vert_line_updates);
}),cljs.core.PersistentArrayMap.EMPTY,cljs.core.range.call(null,cludoku.board.dim.call(null,board)));
});
cludoku.solver.x_wing = (function x_wing(board){var dim_range = cljs.core.range.call(null,cludoku.board.dim.call(null,board));return cljs.core.reduce.call(null,(function (acc,cand){var coords_with_cand = cljs.core.map.call(null,cljs.core.first,cljs.core.filter.call(null,cludoku.solver.with_candidate.call(null,cand),new cljs.core.Keyword(null,"cells","cells",1108448963).cljs$core$IFn$_invoke$arity$1(board)));var by_rows = cljs.core.reduce.call(null,((function (coords_with_cand){
return (function (acc__$1,p__6078){var vec__6079 = p__6078;var x = cljs.core.nth.call(null,vec__6079,0,null);var y = cljs.core.nth.call(null,vec__6079,1,null);return cljs.core.conj.call(null,acc__$1,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x,cljs.core.conj.call(null,cljs.core.get.call(null,acc__$1,x,cljs.core.PersistentHashSet.EMPTY),y)], null));
});})(coords_with_cand))
,cljs.core.PersistentArrayMap.EMPTY,coords_with_cand);var rows_with_two_cands = cljs.core.filter.call(null,((function (coords_with_cand,by_rows){
return (function (p__6080){var vec__6081 = p__6080;var _ = cljs.core.nth.call(null,vec__6081,0,null);var ys = cljs.core.nth.call(null,vec__6081,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,ys),2);
});})(coords_with_cand,by_rows))
,by_rows);var two_cand_rows_by_column = cljs.core.reduce.call(null,((function (coords_with_cand,by_rows,rows_with_two_cands){
return (function (acc__$1,p__6082){var vec__6083 = p__6082;var x = cljs.core.nth.call(null,vec__6083,0,null);var ys = cljs.core.nth.call(null,vec__6083,1,null);return cljs.core.conj.call(null,acc__$1,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [ys,cljs.core.conj.call(null,cljs.core.get.call(null,acc__$1,ys,cljs.core.PersistentHashSet.EMPTY),x)], null));
});})(coords_with_cand,by_rows,rows_with_two_cands))
,cljs.core.PersistentArrayMap.EMPTY,rows_with_two_cands);var x_wing_coords = cljs.core.first.call(null,cljs.core.filter.call(null,((function (coords_with_cand,by_rows,rows_with_two_cands,two_cand_rows_by_column){
return (function (p__6084){var vec__6085 = p__6084;var _ = cljs.core.nth.call(null,vec__6085,0,null);var row_set = cljs.core.nth.call(null,vec__6085,1,null);return cljs.core._EQ_.call(null,cljs.core.count.call(null,row_set),2);
});})(coords_with_cand,by_rows,rows_with_two_cands,two_cand_rows_by_column))
,two_cand_rows_by_column));if((cljs.core.count.call(null,x_wing_coords) > 0))
{var vec__6086 = cljs.core.map.call(null,cljs.core.seq,x_wing_coords);var vec__6087 = cljs.core.nth.call(null,vec__6086,0,null);var y1 = cljs.core.nth.call(null,vec__6087,0,null);var y2 = cljs.core.nth.call(null,vec__6087,1,null);var vec__6088 = cljs.core.nth.call(null,vec__6086,1,null);var x1 = cljs.core.nth.call(null,vec__6088,0,null);var x2 = cljs.core.nth.call(null,vec__6088,1,null);var cell1 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x1,y1], null);var cell2 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x1,y2], null);var cell3 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x2,y1], null);var cell4 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x2,y2], null);var x_wing_cells = new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [cell1,cell2,cell3,cell4], null);return cljs.core.merge.call(null,acc,cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,x_wing_cells,cludoku.board.board_row.call(null,board,x1))),cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,x_wing_cells,cludoku.board.board_row.call(null,board,x2))),cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,x_wing_cells,cludoku.board.board_col.call(null,board,y1))),cludoku.solver.drop_candidates.call(null,cljs.core.PersistentHashSet.fromArray([cand], true),cludoku.solver.skip_cells.call(null,x_wing_cells,cludoku.board.board_col.call(null,board,y2))));
} else
{return acc;
}
}),cljs.core.PersistentArrayMap.EMPTY,dim_range);
});
cludoku.solver.region_rule = (function region_rule(f){return (function (board){var dim = cludoku.board.dim.call(null,board);var row_updates = cljs.core.reduce.call(null,((function (dim){
return (function (acc,i){return cljs.core.merge.call(null,acc,f.call(null,cludoku.board.board_row.call(null,board,i)));
});})(dim))
,cljs.core.PersistentArrayMap.EMPTY,cljs.core.range.call(null,dim));var col_updates = cljs.core.reduce.call(null,((function (dim,row_updates){
return (function (acc,i){return cljs.core.merge.call(null,acc,f.call(null,cludoku.board.board_col.call(null,board,i)));
});})(dim,row_updates))
,cljs.core.PersistentArrayMap.EMPTY,cljs.core.range.call(null,dim));var block_updates = cljs.core.reduce.call(null,((function (dim,row_updates,col_updates){
return (function (acc,i){return cljs.core.merge.call(null,acc,f.call(null,cludoku.board.board_block.call(null,board,i)));
});})(dim,row_updates,col_updates))
,cljs.core.PersistentArrayMap.EMPTY,cljs.core.range.call(null,dim));return cljs.core.merge.call(null,row_updates,col_updates,block_updates);
});
});
cludoku.solver.rules = new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1017277949),"Naked pairs",new cljs.core.Keyword(null,"function","function",2394842954),cludoku.solver.region_rule.call(null,cludoku.solver.naked_pairs)], null),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1017277949),"Candidate in a single cell",new cljs.core.Keyword(null,"function","function",2394842954),cludoku.solver.region_rule.call(null,cludoku.solver.single_cell_candidate)], null),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1017277949),"Candidate lines",new cljs.core.Keyword(null,"function","function",2394842954),cludoku.solver.candidate_lines], null),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1017277949),"X-Wing",new cljs.core.Keyword(null,"function","function",2394842954),cludoku.solver.x_wing], null)], null);
