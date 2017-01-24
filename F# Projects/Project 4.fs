module hw4

(* Assignment 4 *) (* Do not edit this line. *)
(* Student name: Pentcho Tchomakov, Id Number: 260632861 *) (* Edit this line. *)

type typExp =
| TypInt
| TypVar of char
| Arrow of typExp * typExp
| Lst of typExp

type substitution = (char * typExp) list

(* check if a variable occurs in a term *)
let rec occurCheck (v: char) (tau: typExp) : bool = 
    match tau with
    | TypInt -> false
    | TypVar char -> if (char = v) then true else false
    | Arrow (typExp1, typExp2) -> if (occurCheck v typExp1 || occurCheck v typExp2) then true else false
    | Lst exp -> occurCheck v exp

(* substitute typExp tau1 for all occurrences of type variable v in typExp tau2 *)
(* Replacing tau1 with all v in tau2 *)
let rec substitute (tau1 : typExp) (v : char) (tau2 : typExp) : typExp =
    match tau2 with
    | TypInt -> tau2
    | TypVar char -> if (char = v) then tau1 else tau2
    | Arrow(typExp1, typExp2) -> Arrow(substitute tau1 v typExp1, substitute tau1 v typExp2)
    | Lst exp -> Lst (substitute tau1 v exp)
    
let applySubst (sigma: substitution) (tau: typExp) : typExp =
    List.fold(fun acc x -> substitute (snd(x)) (fst(x)) acc) tau sigma
    
(* This is a one-line program *)

let rec unify (tau1: typExp) (tau2:typExp) : substitution =
    match tau1 with
    | TypInt ->
        match tau2 with
        | TypInt -> []
        | TypVar char -> [(char, tau1)]
        | Arrow (typExp1, typExp2) -> failwith "Not unifiable"
        | Lst exp -> failwith "Not unifiable"
    | TypVar char1 ->
        match tau2 with
        | TypInt -> [(char1, tau2)]
        | TypVar char2 -> [(char1, tau2)]
        | Arrow (typExp1, typExp2) -> 
            if ((occurCheck char1 typExp1) || (occurCheck char1 typExp2)) then failwith "Failed occurs check" 
            else [(char1, tau2)]
        | Lst exp ->
            if (occurCheck char1 exp) then failwith "Failed occurs check" 
            else [(char1, tau2)]
    | Arrow (typExp1, typExp2) ->
        match tau2 with
        | TypInt -> failwith "Clash in principal type constructor"
        | TypVar char ->
            if (occurCheck char typExp1) || (occurCheck char typExp2) then failwith "Failed occurs check" 
            else [(char, tau1)]
        | Arrow (typExp3, typExp4) ->   let newTyp2 = applySubst (unify typExp1 typExp3) typExp2 in
                                        let newTyp4 = applySubst (unify typExp1 typExp3) typExp4 in
                                        (unify typExp1 typExp3)@(unify newTyp2 newTyp4)
        | Lst exp -> failwith "Clash in principal type constructor"
    | Lst exp1 ->
        match tau2 with
        | TypInt -> failwith "Clash in principal type constructor"
        | TypVar char ->
            if occurCheck char exp1 then failwith "Failed occurs check" 
            else [(char, tau1)]
        | Arrow (typExp1, typExp2) -> failwith "Clash in principal type constructor"
        | Lst exp2 -> unify exp1 exp2

(* Use the following signals if unification is not possible:

failwith "Clash in principal type constructor"
failwith "Failed occurs check"
failwith "Not unifiable"

*)




let te4 = Arrow(TypInt, Arrow(TypVar 'c', TypVar 'a'));;

//val te4 : typExp = Arrow(TypInt,Arrow (TypVar 'c',TypVar 'a'));;

let te3 = Arrow(TypVar 'a',Arrow (TypVar 'b',TypVar 'c'));;

let tau1 = Arrow(TypVar 'a', TypVar 'a');;
let tau2 = Arrow(TypVar 'b', TypVar 'c');;
//val te3 : typExp = Arrow(TypVar 'a',Arrow (TypVar 'b',TypVar 'c'));;

//unify tau1 tau2;;
unify te3 te4;;
//val it a: substitution = [('c', TypInt); ('b', TypVar 'c'); ('a', TypInt)]
let result = [('a', TypInt); ('b', TypVar 'c'); ('c', TypInt)];;

//val result : substitution = [('c', TypInt); ('b', TypVar 'c'); ('a', TypInt)]

applySubst (unify te3 te4) te3;;
//val it : typExp = Prod (TypInt,Arrow (TypInt,TypInt))
applySubst result te4;;
//val it : typExp = Prod (TypInt,Arrow (TypInt,TypInt))





    

    
(*

> let te4 = Prod(TypInt, Arrow(TypVar 'c', TypVar 'a'));;

val te4 : typExp = Prod (TypInt,Arrow (TypVar 'c',TypVar 'a'))

> let te3 = Prod (TypVar 'a',Arrow (TypVar 'b',TypVar 'c'));;

val te3 : typExp = Prod (TypVar 'a',Arrow (TypVar 'b',TypVar 'c'))

> unify te3 te4;;
val it : substitution = [('c', TypInt); ('b', TypVar 'c'); ('a', TypInt)]
> let result = it;;

val result : substitution = [('c', TypInt); ('b', TypVar 'c'); ('a', TypInt)]

> applySubst result te3;;
val it : typExp = Prod (TypInt,Arrow (TypInt,TypInt))
> applySubst result te4;;
val it : typExp = Prod (TypInt,Arrow (TypInt,TypInt))

*)


    