(* Assignment 1 *) (* Do not edit this line. *)
(* Student name: Pentcho Tchomakov, Id Number: 260632861 *) (* Edit this line. *)

(* Question 1 *) (* Do not edit this line. *)

(* val sumlist : l:float list -> float *)
let rec sumlist l:float = 
    match l with
    | [] -> 0.0
    | x::xs -> (x:float) + sumlist(xs)

(* val squarelist : l:float list -> float list *)
let rec squarelist l =
    let square u = u * u
    match l with
    | [] -> []
    | x::xs -> square(x)::(squarelist xs)

(* val mean : l:float list -> float *)
let mean l:float =
    if (List.isEmpty l) then 0.0 
    else (sumlist(l))/(float(List.length l));;

(* val mean_diffs : l:float list -> float list *)
let mean_diffs l =
    let actual_mean = mean(l)
    let rec diffs l =
         match l with
        | [] -> []
        | x::xs -> (x - actual_mean)::(diffs xs)
    diffs l


(* val variance : l:float list -> float *)
let variance l = 
    sumlist(squarelist(mean_diffs(l)))/(float(List.length l))
    
(* End of question 1 *) (* Do not edit this line. *)    
    
(* Question 2 *) (* Do not edit this line. *)

(*val memberof : 'a * 'a list -> bool when 'a : equality *)
let rec memberof (e, l) = 
    match l with
    | [] -> false
    | x::xs -> if (x = e) then true else memberof(e, xs) 
    

(* val remove : 'a * 'a list -> 'a list when 'a : equality *)
let rec remove (e, l) = 
    match l with
    | [] -> []
    | x::xs -> if(memberof(e, l) && e = x) then remove(e, xs) else x::remove(e, xs)

(* End of question 2 *) (* Do not edit this line *)

(* Question 3 *) (* Do not edit this line *)

(* val isolate : l:'a list -> 'a list when 'a : equality *)
let rec isolate l =
    match l with
    | [] -> []
    |  x::xs -> if(memberof(x,xs)) then isolate(xs) else x::(isolate(xs))
    

(* End of question 3 *) (* Do not edit this line *)

 (* Question 4 *) (* Do not edit this line *)

(* val common : 'a list * 'a list -> 'a list when 'a : equality *)
(*ha for head of a, and ta for tail of a*)
let rec common (a1, b) =
    let a = isolate(a1)
    match a, b with
    | ([], b) -> []
    | (a, []) -> []
    | (ha::ta, hb::tb) -> 
        if (memberof(ha, b)) then ha::(common(ta, b)) else common(ta,b)
    

(* End of question 4 *) (* Do not edit this line *)

(* Question 5 *) (* Do not edit this line *)

(* val split : l:'a list -> 'a list * 'a list *)
let rec split (l) = 
    match l with
    | [] -> [], []
    | x::[] -> [x], []
    | x1::x2::xs -> let left, right = split(xs) in x1::left, x2::right

let rec split2(list) =
    match list with
    | [] -> [], []
    | h::[] -> [h], []
    | h1::h2::t -> let l,r = split(t) in h1::l, h2::r

(* val merge : 'a list * 'a list -> 'a list when 'a : comparison *)
let rec merge (a, b) = 
    match a, b with
    | a, [] -> a
    | [], b -> b
    | ha::ta, hb::tb ->
        if (ha <= hb) then ha::(merge (ta, b))
        else hb::(merge (a, tb))

(*val mergesort : l:'a list -> 'a list when 'a : comparison *)
let rec mergesort l =
    match l with
    | [] -> []
    | [x] -> [x]
    | _ -> let l1, l2 = split l
            in merge((mergesort l1), (mergesort l2))

(* End of question 5 *) (* Do not edit this line *)

let aList = [1.0..10.0];;
let bList = [1.0;2.0;];;

[<EntryPoint>]
let main argv = 
    let (result) = common (aList, bList)
    printfn "%A" result
    System.Console.ReadKey() |> ignore
    0 // return an integer exit code

