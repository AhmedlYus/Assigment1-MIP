
# Task 1: identify $\mathcal{V}$ and $\mathcal{W}$

### find()

$\mathcal{V}$ = {top, i.val, i.next}

$\mathcal{W}$ = ∅



### push()

$\mathcal{V}$ = {top}

$\mathcal{W}$ = {top, (new.next)}

Unsure about new.top, its technically in shared memory but is unreachable before it becomes top.next

### pop()

$\mathcal{V}$ = {top, top.next, oldTop.val}

oldTop.val can also be considered top.val

$\mathcal{W}$ = {top}

