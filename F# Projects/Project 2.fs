module hw2

(* Assignment 2 *) (* Do not edit this line. *)
(* Student name: Pentcho Tchomakov, Id Number: 260632861 *) (* Edit this line. *)

(* In the template below we have written the names of the functions that
you need to define.  You MUST use these names.  If you introduce auxiliary
functions you can name them as you like except that your names should not
clash with any of the names we are using.  We have also shown the types
that you should have.  Your code must compile and must not go into infinite
loops.  You are free to use library functions now.  But loops are BANNED
as is any kind of imperative code based on updating values.  You can make functions
recursive at top level or modify them so that the recursion is hidden inside.  The
only things we really insist on are: (a) use the names we have used and (b) the
functions must have the types that we have shown.  We hope by now you understand that
everywhere where it says failwith "Error - not implemented" you have to remove this
and replace it with your code.  *)

(* Question 1 *)

(* val deriv : f:(float -> float) * dx:float -> x:float -> float *)
let deriv (f, dx: float) = 
    fun x -> ((f(x + dx) - f(x))/dx)

(* val newton : f:(float -> float) * guess:float * tol:float * dx:float -> float *)
let rec newton(f, guess:float, tol:float, dx:float) = 
    if (abs(f guess) > tol) then
        let newGuess = guess - ((f guess)/deriv(f, dx) guess) in newton(f, newGuess, tol, dx)
    else guess

let make_cubic(a:float,b,c) = fun x -> (x*x*x + a * x*x + b*x + c);;


(* Question 2 *)

type term = float * int
type poly = term list

exception EmptyList

(* Multiply a term by a polynomial. *)
(* val mtp : t:term * p:poly -> poly *)
let mtp(t:term, p:poly):poly =
    let t1 = fst t
    let t2 = snd t
    let rec multiply (t1:float, t2:int, p:poly) =
        match p with
        | [] -> []
        | x::xs -> (fst x * t1, snd x + t2)::(multiply(t1, t2, xs))
    multiply(t1, t2, p)


(* Add a term to a polynomial. *)
(* val atp : t:term * p:poly -> poly *)
let rec atp(t:term,p:poly):poly = 
    let t1 = fst t
    let t2 = snd t
    match p with
    | [] -> [t]
    | x::xs -> if(snd x = t2) then (fst x + t1, snd x)::xs
               elif(snd x < t2) then t::p
               else x::atp(t, xs)
   

(* Add two polynomials.  The result must be properly represented. This means you
cannot have more than one term with the same exponent, you should not have a
term with a zero coefficient, except when the whole polynomial is zero and the
(* val addpolys : p1:poly * p2:poly -> poly *)
terms should be decreasing order of coefficients. *)
let rec addpolys(p1:poly,p2:poly):poly =
    match (p1, p2) with
    | ([],[]) -> []
    | ([], p2) -> p2
    | (p1, []) -> p1
    | poly1h::poly1t, poly2h::poly2t -> let p11 = fst poly1h in 
                                        let p12 = snd poly1h in 
                                        let p21 = fst poly2h in
                                        let p22 = snd poly2h in
                                        if(p12 = p22) then (p11 + p21, p12)::addpolys(poly1t, poly2t)
                                        elif(p12 > p22) then poly1h::addpolys(poly1t,p2)
                                        else poly2h::addpolys(p1, poly2t)




(* Multiply two polynomials.  All the remarks above apply here too. Raise an
exception if one of the polynomials is the empty list. *)
(* val multpolys : p1:poly * p2:poly -> poly *)
let rec multpolys(p1:poly,p2:poly) =
    match (p1, p2) with
    | ([],[]) -> []
    | ([], p2) -> []
    | (p1, []) -> []
    | (poly1h::poly1t, poly2h::poly2t) -> addpolys(mtp(poly1h, p2), multpolys(poly1t, p2))


(* This is the tail-recursive version of Russian peasant exponentiation.  I have
done it for you.  You will need it for the next question.  *)
let exp(b:float, e:int) =
  let rec helper(b:float, e:int, a: float) =
    if (b = 0.0) then 0.0
    elif (e = 0) then a
    elif (e % 2 = 1) then helper(b, e-1, b*a)
    else helper(b*b, e/2, a)
  helper(b,e, 1.0)

(* Here is how you evaluate a term. *)
let evalterm (v:float) ((c,e):term) = if (e = 0) then c else c * exp(v,e)

(* Evaluate a polynomial viewed as a function of the indeterminate.  Use the function
above and List.fold and List.map and a dynamically created function for a one-line
answer.  *)
(* val evalpoly : p:poly * v:float -> float *)
let rec evalpoly(p:poly,v:float):float =
    List.fold (fun acc elem -> acc + elem) 0.0 (p |> List.map(fun x -> evalterm v x))
    


(* Compute the derivative of a polynomial as a symbolic representation.  Do NOT use
deriv defined above.  I want the answer to be a polynomial represented as a list.
I have done a couple of lines so you can see how to raise an exception.  *)
(*  val diff : p:poly -> poly *)
let rec diff (p:poly):poly = 
  match p with 
    | [] -> []
    | polyh::polyt -> let ph1 = fst polyh in
                      let ph2 = snd polyh in
                      if (ph2 = 0) then []
                      else (ph1*(float)ph2, ph2-1)::diff(polyt)


(* Question 3 *)
(* Most of these functions are only one or two lines.  One of them, the longest is
about 5 lines.  However, they require some thought.  They are short because I used
the Set library functions wherever I could.  I especially found Set.fold useful. *)

type Country = string;;
type Chart = Set<Country*Country>;;
type Colour = Set<Country>;;
type Colouring = Set<Colour>;;

(* This is how you tell that two countries are neghbours.  It requires a chart. *)
(* val areNeighbours : ct1:'a -> ct2:'a -> chart:Set<'a * 'a> -> bool when 'a : comparison*)
let areNeighbours ct1 ct2 chart = Set.contains (ct1,ct2) chart || Set.contains (ct2,ct1) chart;;


(* The colour col can be extended by the country ct when they are no neighbours
according to chart.*)
(* val canBeExtBy : col:Set<'a> -> ct:'a -> chart:Set<'a * 'a> -> bool when 'a : comparison *)
let canBeExtBy (col:Colour) (ct:Country) (chart:Chart) = 
    if (col |> Set.exists(fun x -> areNeighbours x ct chart)) then false else true

(* Here you have to extend a colouring by a fixed country. *)
(* val extColouring : chart:Chart -> c olours:Colouring -> country:Country -> Set<Set<Country>> *)
let rec extColouring (chart: Chart) (colours : Colouring) (country : Country) = 
    if (Set.isEmpty colours) then Set.add(Set.add country Set.empty) Set.empty
    else let element = Set.minElement colours
         let restOfColours = Set.remove element colours
         if (canBeExtBy element country chart) then Set.add (Set.add country element) restOfColours
         else Set.add element (extColouring chart restOfColours country)


(* This collects the names of the countries in the chart.  A good place
to use Set.fold *) 
(* val countriesInChart : chart:Chart -> Set<Country> *)
let countriesInChart (chart : Chart) =   
    let set1 = Set.fold (fun (accumulator: (Set<Country>)) element -> 
        Set.add (snd element) accumulator) Set.empty chart
    let set2 = Set.fold (fun (accumulator: (Set<Country>)) element -> 
        Set.add (fst element) accumulator) Set.empty chart
    Set.union set2 set1
 

(* Here is the final function.  It is also most conveniently done with Set.fold *)
(* val colourTheCountries : chart:Chart -> Colouring *)
let colourTheCountries (chart: Chart) =  
    Set.fold (fun counter country -> extColouring chart counter country) Set.empty (countriesInChart chart)


(* Question 4 *)

(* These functions are a bit longer but easier to code than Q3.  It is very similar
to the evaluator that I showed in class.  However I have used the Option type so that
the program gracefully returns None if no value is found.  This can be preferred to
raising an exception in some situations.  Learn option types from the web.  *)

type Exptree =
  | Const of int 
  | Var of string 
  | Add of Exptree * Exptree 
  | Mul of Exptree * Exptree;;

type Bindings = (string * int) list;;

(* The bindings are stored in a list rather than a BST for simplicity.  The
list is sorted by name, which is a string. *)
(* val lookup : name:string * env:Bindings -> int option *)
let rec lookup(name:string, env: Bindings) = 
    match env with
    | [] -> None
    | x::xs -> if(fst x = name) then Some(snd x) else lookup(name, xs)

(* Insert a new binding.  If the name is already there then the new binding should
be put in front of it so that the lookup finds the latest binding.  *)
(* val insert : name:string * value:int * b:Bindings -> (string * int) list*)
let rec insert(name:string, value: int, b: Bindings) =
    match b with
    | [] -> [(name, value)] 
    | x::xs -> if(fst x >= name) then (name, value)::x::xs
               else x:: insert(name, value, xs)          

(* The recursive evaluator.  You have to match on the exp.  If a variable is not
found return None.  If you are applying an operator to None and something else the
answer is None.  This leads to a program of about 20 lines but it is conceptually
very easy.  *)
(* val eval : exp:Exptree * env:Bindings -> int option  *)
// Added comments to make it easier to go through
let rec eval(exp : Exptree, env:Bindings) = 
    // Expression patern matching
    match exp with
    | Const constant -> Some constant
    | Var variable -> lookup(variable, env)
    // Addition pattern matching 
    // (if not a constant or a variable)
    | Add (first, second) ->
        match (eval(first, env), eval(second, env)) with
        // If both are empty, return None
        | (None, None) -> None
        // If any of them are empty, return None
        | (None, _) -> None
        | (_, None) -> None
        // If both, then do the "actual" addition by extraction the integers and then adding them
        | (Some a, Some b) -> Some (a+b)
    // Multiplication pattern matching 
    // (if not a constant, a variable or an addition)
    | Mul (first, second) -> 
        match (eval(first ,env), eval(second, env)) with
        // Same concept as addition
        | (None, None) -> None
        | (None, _) -> None
        | (_, None) -> None
        // If both, then do the "actual" multiplication by extraction the integers and then multipling them
        | (Some a, Some b) -> Some (a*b)

let poly1:poly = [(3.0,5);(2.0,2);(7.0,1);(1.5,0)];;
let term1:term = (1.5,2);;
let poly2 = mtp(term1, poly1);;
let poly4:poly = addpolys(poly2, poly1);;
let t2:term = (7.0,3);;

let myWorld:Chart = Set.ofList [("Andorra","Benin");("Andorra","Canada");("Andorra","Denmark");("Benin","Canada"); ("Benin","Denmark");("Canada","Denmark");("Estonia","Canada");("Estonia","Denmark");("Estonia","Finland");("Finland","Greece");("Finland","Benin");("Greece","Benin");("Greece","Denmark");("Greece","Estonia")];;
let myColour:Colour = Set.ofList ["Andorra";"Estonia"]

[<EntryPoint>]
let main argv = 
    let result = colourTheCountries myWorld
    printfn "%A" result
    System.Console.ReadKey() |> ignore
    0 