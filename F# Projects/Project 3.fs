module hw3

(* Assignment 3 *) (* Do not edit this line. *)
(* Student name: Pentcho Tchomakov, Id Number: 260632861 *) (* Edit this line. *)

(* Question 1 *)
type Cell = { data : int; next : RList}
and RList = Cell option ref

let c1 = {data = 1; next = ref None}
let c2 = {data = 2; next = ref (Some c1)}
let c3 = {data = 3; next = ref (Some c2)}
let c4 = {data = 5; next = ref (Some c3)}


(* This converts an RList to an ordinary list. *)
let rec displayList (c : RList) =
  match !c with
    | None -> []
    | Some { data = d; next = l } -> d :: (displayList l)

(* This may be useful.  You don't have to use it.*)
let cellToRList (c:Cell):RList = ref (Some c)

(* Example for testing. *)
let bigger(x:int, y:int) = 
    if x > y then true else false
                                 
let rec insert comp (item : int) (list: RList) =
    match !list with
    | None -> list:= Some ({data = item; next = ref None})
    | Some {data = d; next = l} ->
        if comp(item, d) = true then list:= Some ({data = item; next = l}); (insert bigger d l)
        else insert bigger item l

(* Question 2 *)
type transaction = Withdraw of int | Deposit of int | CheckBalance

let make_protected_account(opening_balance : int, password : string) =
    let totalBalance = ref opening_balance
    let pass = ref password
    fun(a : string, b : transaction) ->
        match b with
        | Withdraw(m) ->
            if((!totalBalance > m) && (!pass = a)) then (totalBalance := !totalBalance - m) ; (printf "The new balancne is %i" !totalBalance)
            elif (a <> !pass) then printfn "Incorrect Password."
            else printfn "Insufficient funds."
        | Deposit(m) ->
            if (!pass = a) then (totalBalance := !totalBalance + m) ; (printfn "The new balance is %i" !totalBalance)
            else (printf "Incorrect Password.")
        | CheckBalance ->
            if (!pass = a) then printfn "The balance is %i." !totalBalance
            else printf "Incorrect Password."

(* Question 3 *)

open System.Collections.Generic;;

type ListTree<'a> = Node of 'a * (ListTree<'a> list)

let bfIter f ltr =
    let bfsQueue = Queue<ListTree<'a>>()
    bfsQueue.Enqueue(ltr)
    while(bfsQueue.Count > 0) do
        let (Node(x,lst)) = bfsQueue.Dequeue()
        f(x)
        List.iter (fun y -> bfsQueue.Enqueue(y)) lst

(*   This is how you set up a new Queue: let todo = Queue<ListTree<'a>> () *)

let n5 = Node(5,[])
let n6 = Node(6,[])
let n7 = Node(7,[])
let n8 = Node(8,[])
let n9 = Node(9,[])
let n10 = Node(10,[])
let n12 = Node(12,[])
let n11 = Node(11,[n12])
let n2 = Node(2,[n5;n6;n7;n8])
let n3 = Node(3,[n9;n10])
let n4 = Node(4,[n11])
let n1 = Node(1,[n2;n3;n4])


(* How I tested the BFS program.   
bfIter (fun n -> printfn "%i" n) n1;;

*)

[<EntryPoint>]
let main argv = 
    bfIter (fun n -> printfn "%i" n) n1;
    //printfn "%A" result
    System.Console.ReadKey() |> ignore
    0 // return an integer exit code



