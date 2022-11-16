//assignment_2_stack and queue

//use custom-built linkedList to simulate stack
import java.util.*;

class Main
{
    public static void main(String[] args)
    {
        MyLinkedList list=new MyLinkedList(0);
        list.push(1);
        list.push(2);
        list.push(3);

        System.out.println("push 3 val");
        for(int i=0;i<list.size;i++)    System.out.println(list.get(i).val);
        System.out.println();

        System.out.println("size : "+list.size());
        System.out.println();


        System.out.println("pop 1 val");
        list.pop();
        for(int i=0;i<list.size;i++)    System.out.println(list.get(i).val);
        System.out.println();

        System.out.println("size : "+list.size());
        System.out.println();

        System.out.println("peek : "+list.peek().val);
    }
}
class MyLinkedList
{
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;												//另外自己將head加在鏈結前面，ex. 1->2->4加入head變成	head->1->2->4
    //****加入head之前和之後size都是3
    Queue<Integer> queue=new LinkedList<>();
    //初始化链表

    public MyLinkedList(double val)
    {
        size = 0;
        head = new ListNode(0);
    }
    public MyLinkedList(char ch)
    {
        size = 0;
        head = new ListNode(ch);
    }

    //获取第index个节点的数值
    public ListNode peek()
    {
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i < size; i++) 					    //ex. 	1->2->4加入head變成	head->1->2->4	//如果寫for (int i = 0; i < index; i++)代表找到index的左邊一個數字
        {														//index 0, 1, 2			index  0, 1, 2, 3	//若寫for (int i = 0; i < index + 1; i++)代表找到index
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, double val)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    public void addAtIndex(int index, char ch)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(ch);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    //在链表的最后插入一个节点
    public void push(double val)
    {
        addAtIndex(size, val);
    }
    public void push(char ch)
    {
        addAtIndex(size, ch);
    }
    //删除最後一个节点
    public ListNode pop()
    {
        ListNode pred = head;
        for (int i = 0; i < size-1; i++) 			//找到最後1個數字
        {
            pred = pred.next;
        }
        ListNode ans=pred.next;
        pred.next = pred.next.next;

        size--;
        return ans;
    }

    public ListNode get(int index)
    {
        //如果index非法，返回-1
        if (index < 0 || index >= size) 						//ex. 	1->2->4，鏈表size為3，但是index為
        {														//index 0, 1, 2
            return null;
        }
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i < index + 1; i++) 					//ex. 	1->2->4加入head變成	head->1->2->4	//如果寫for (int i = 0; i < index; i++)代表找到index的左邊一個數字
        {														//index 0, 1, 2			index  0, 1, 2, 3	//若寫for (int i = 0; i < index + 1; i++)代表找到index
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public int size()
    {
        return size;
    }
}

class ListNode
{
    double val;
    char ch;
    ListNode next;
    ListNode(){}				//constructor建構元
    ListNode(double val) 			//constructor建構元
    {
        this.val=val;
    }

    ListNode(char ch)
    {
        this.ch=ch;
    }
}


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//use custom-built linkedList to simulate "+ - * /"
//reference:https://blog.csdn.net/chmfy006/article/details/107734940
import java.util.*;

class Main
{

    public static void main(String[] args)
    {
        String str="3*4/2+5*6+5*6";
        double result=calculate(str);
        if(error==1)    System.out.println("NaN");
        else            System.out.println(result);
    }


    private static int NP = 1;												//标记进栈数字元素的正负性
    private static int error=0;
    private static boolean NPCanUse = true;								    //NP标记是否是激活状态
    private static boolean flag = true;									    //用于标记前一个符号是否是')'，若是，则当前数字字符串一定为空
    private static MyLinkedList figure = new MyLinkedList(0);				//数字栈
    private static MyLinkedList operator = new MyLinkedList(' ');			//符号栈
    private static StringBuffer figureString = new StringBuffer();			//数字字符串，用于暂时存放读取到的单个数字字符，并将它们拼接成一个完整的数字

    /**
     * @description 计算运算式（主程序）
     * 				<br>
     * 				根据传入的代表算式的字符串计算结果并返回
     * @param formula 代表算式的字符串
     * @return 计算结果
     */
    public static double calculate(String formula)
    {
        formula += "#";
        operator.push('#');
        for (int i = 0;i < formula.length();i ++)
        {
            if(formula.charAt(i)==' ')  continue;                           //避開空白
            //读取到"数字"时进行的操作
            if (getPriority(formula.charAt(i))==-5)
            {
                doWhenCharIsNotOperator(formula.charAt(i));
            }
            //读取到"运算符"时进行的操作
            else if(i==0 && (formula.charAt(i)=='+' || formula.charAt(i)=='-'))
            {
                NP *= formula.charAt(i) == '-' ? -1: 1;
                //当读取到"加減乘除号"，下一个加减号视为正负号，NP激活
                if (getPriority(formula.charAt(i)) == 1 || getPriority(formula.charAt(i)) == 2)
                    NPCanUse = true;
            }
            else if (i==0 && (formula.charAt(i)=='*' || formula.charAt(i)=='/'))
            {
                error=1;
                return 0;
            }
            else if (i==formula.length()-2 && (formula.charAt(i)=='+' || formula.charAt(i)=='-' || formula.charAt(i)=='*' || formula.charAt(i)=='/'))
            {
                error=1;
                return 0;
            }
            else if(getPriority(formula.charAt(i))!=-6)
            {
                doWhenCharIsOperator(formula.charAt(i),formula.charAt(i - 1));
            }
            //读取到其他東西時,出現error
            else if(getPriority(formula.charAt(i))==-6)
            {
                error=1;
            }
        }
        if (getPriority(operator.pop().ch) != -4)
        {

            error = 1;                                                      //字符串读取完成，但是符号栈内仍然有运算符
            return 0;
        }
        else
            return figure.pop().val;
    }


    //获取字符c的优先级并返回
    private static int getPriority(char c)
    {
        switch(c)
        {
            case '+':return 1;
            case '-':return 1;
            case '*':return 2;
            case '/':return 2;
            case '#':return -4;
            default:
            {
                if(c>=48 && c<=57)  return -5;
                else                return -6;              //其他字母
            }
        }
    }

    //根据运算符和两个数字计算结果
    private static double operate(char c,double u,double d)
    {
        switch(c)
        {
            case '+':return u + d;
            case '-':return u - d;
            case '*':return u * d;
            case '/':
            {
                if(d==0)									//避免分母=0
                {
                    error=1;
                    return 0;
                }
                else    return u / d;
            }
            default:return 0;
        }
    }


    /**
     * @description 当前读取到的字符不是运算符时进行的操作
     * 				<br>
     * 				说明读取到的字符是数字，将它接到数字字符串之后
     * 				<br>
     * 				由于下一个字符一定不会是正负号，所以激活标记睡眠
     *
     * @param currentCharacter 当前读取到的字符
     */
    private static void doWhenCharIsNotOperator(char currentCharacter)
    {
        figureString.append(currentCharacter);
        NPCanUse = false;		//NP标记休眠
    }


    /**
     * @description 当前读取到的字符是运算符时进行的操作
     * 				<br>
     *
     * @param currentCharacter 当前读取到的字符
     */
    private static void doWhenCharIsOperator(char currentCharacter,char proCharacter)
    {
        //"*/","-*"連在一起
        if((getPriority(currentCharacter) == 2 && getPriority(proCharacter) == 2) || (getPriority(currentCharacter) == 2 && getPriority(proCharacter) == 1))
        {
            error=1;
            return;
        }
        //当前符号是"加減乘除号"且正负号标记激活状态，即当前符号不是正负号
        else if (!((getPriority(currentCharacter) == 1 || getPriority(currentCharacter) == 2) && (NPCanUse == true)))
        {
            pushFigure(currentCharacter,proCharacter);		//尝试将数字字符串进栈数字栈（有些特殊情况不能进栈）
            operate(currentCharacter);			//尝试运算（有些情况当前字符不会参与运算）
        }
        //当前符号是正负号，并且为符号时，正负号标记*-1
        else
            NP *= currentCharacter == '-' ? -1: 1;
        //当读取到"加減乘除号"，下一个加减号视为正负号，NP激活
        if (getPriority(currentCharacter) == 1 || getPriority(currentCharacter) == 2)
            NPCanUse = true;
    }



    /**
     * @description 数字栈进栈
     * 				<br>
     * 				根据当前符号判断是否将数字符号串内的内容转为数字并进栈数字栈
     * 				<br>
     * 				并重置NP标记，并将其改为休眠状态
     * 				<br>
     * @param currentCharacter 当前读取的符号
     * @param proCharacter 当前读取的符号的上一个符号
     */
    private static void pushFigure(char currentCharacter,char proCharacter)
    {

        figure.push(Double.valueOf(figureString.toString()) * NP);		//将数字字符串的内容转为数字并进栈数字栈
        figureString.setLength(0);										//清空数字字符串
        NP = 1;					//重置NP及其激活状态
        NPCanUse = false;		//NP休眠
    }



    /**
     * @description 运算操作
     * 				<br>
     * 				根据当前符号的意义判断是否进行运算，如果是则进行运算
     * 				<br>
     * 				如果当前符号暂时不能参与运算，根据当前符号做相应的处理
     * 				<br>
     * @param currentCharacter 当前读取的符号
     */
    private static void operate(char currentCharacter)
    {
        //尝试将当前符号拿来参与运算
        while((getPriority(currentCharacter) <= getPriority(operator.peek().ch)) && operator.peek().ch != '(' && operator.peek().ch != '#')
        {
            double d = figure.pop().val;
            double u = figure.pop().val;
            figure.push(operate(operator.pop().ch,u,d));
        }

        operator.push(currentCharacter);                //運算完放回去operator stack
    }
}

class MyLinkedList
{
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;												//另外自己將head加在鏈結前面，ex. 1->2->4加入head變成	head->1->2->4
    //****加入head之前和之後size都是3
    Queue<Integer> queue=new LinkedList<>();
    //初始化链表

    public MyLinkedList(double val)
    {
        size = 0;
        head = new ListNode(0);
    }
    public MyLinkedList(char ch)
    {
        size = 0;
        head = new ListNode(ch);
    }

    //获取第index个节点的数值
    public ListNode peek()
    {
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i < size; i++) 					    //ex. 	1->2->4加入head變成	head->1->2->4	//如果寫for (int i = 0; i < index; i++)代表找到index的左邊一個數字
        {														//index 0, 1, 2			index  0, 1, 2, 3	//若寫for (int i = 0; i < index + 1; i++)代表找到index
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, double val)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    public void addAtIndex(int index, char ch)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(ch);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    //在链表的最后插入一个节点
    public void push(double val)
    {
        addAtIndex(size, val);
    }
    public void push(char ch)
    {
        addAtIndex(size, ch);
    }
    //删除最後一个节点
    public ListNode pop()
    {
        ListNode pred = head;
        for (int i = 0; i < size-1; i++) 			//找到最後1個數字
        {
            pred = pred.next;
        }
        ListNode ans=pred.next;
        pred.next = pred.next.next;

        size--;
        return ans;
    }

    public int size()
    {
        return size;
    }
}

class ListNode
{
    double val;
    char ch;
    ListNode next;
    ListNode(){}				//constructor建構元
    ListNode(double val) 			//constructor建構元
    {
        this.val=val;
    }

    ListNode(char ch)
    {
        this.ch=ch;
    }
}



//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//use custom-built linkedList to simulate queue
import java.util.*;

class Main
{
    public static void main(String[] args)
    {
        MyLinkedList list=new MyLinkedList();
        list.enqueue(1);
        list.enqueue(3);
        list.enqueue(5);
        System.out.println(list.size());
        System.out.println(list.poll());
        System.out.println(list.size());
    }
}

class MyLinkedList
{
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;												//另外自己將head加在鏈結前面，ex. 1->2->4加入head變成	head->1->2->4
    //****加入head之前和之後size都是3
    //初始化链表
    public MyLinkedList()
    {
        size = 0;
        head = new ListNode(0);
    }

    //获取第index个节点的数值
    public int poll()
    {
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i < 1; i++) 					//ex. 	1->2->4加入head變成	head->1->2->4	//如果寫for (int i = 0; i < index; i++)代表找到index的左邊一個數字
        {														//index 0, 1, 2			index  0, 1, 2, 3	//若寫for (int i = 0; i < index + 1; i++)代表找到index
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }


    //在链表的最后插入一个节点
    public void enqueue(int val)
    {
        addAtIndex(size, val);
    }

    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    //删除第index个节点
    public int dequeue()
    {
        ListNode pred = head;
        for (int i = 0; i < 0; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        int value=pred.next.val;
        pred.next = pred.next.next;

        size--;
        return value;
    }

    public int size()
    {
        return size;
    }
}


class ListNode
{
    int val;
    ListNode next;
    ListNode(){}				//constructor建構元
    ListNode(int x) 			//constructor建構元
    {
        this.val=x;				//寫成val=x也可以，用來設定第6行val初始值
    }
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------
//use 2 queue to simulate stack
import java.util.*;

class Main
{
    public static void main(String[] args)
    {
        queue stack=new queue();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        System.out.println(stack.peek());
    }
}

class queue
{
    MyLinkedList queue1;
    MyLinkedList queue2;

    public queue()
    {
        queue1=new MyLinkedList();
        queue2=new MyLinkedList();
    }

    public void push(int x)
    {
        queue2.enqueue(x); // 先放在辅助队列中
        while (queue1.size()!=0){
            queue2.enqueue(queue1.dequeue());
        }
        MyLinkedList queueTemp;
        queueTemp = queue1;
        queue1 = queue2;
        queue2 = queueTemp; // 最后交换queue1和queue2，将元素都放到queue1中
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop()
    {
        return queue1.dequeue(); // 因为queue1中的元素和栈中的保持一致，所以这个和下面两个的操作只看queue1即可
    }

    /** Get the top element. */
    public int peek()
    {
        return queue1.poll();
    }

}

class MyLinkedList
{
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;												//另外自己將head加在鏈結前面，ex. 1->2->4加入head變成	head->1->2->4
    //****加入head之前和之後size都是3
    //初始化链表
    public MyLinkedList()
    {
        size = 0;
        head = new ListNode(0);
    }

    //获取第index个节点的数值
    public int poll()
    {
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i < 1; i++) 					//ex. 	1->2->4加入head變成	head->1->2->4	//如果寫for (int i = 0; i < index; i++)代表找到index的左邊一個數字
        {														//index 0, 1, 2			index  0, 1, 2, 3	//若寫for (int i = 0; i < index + 1; i++)代表找到index
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }


    //在链表的最后插入一个节点
    public void enqueue(int val)
    {
        addAtIndex(size, val);
    }

    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val)
    {
        if (index > size)
        {
            return;
        }
        if (index < 0)
        {
            index = 0;
        }

        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;

        size++;
    }

    //删除第index个节点
    public int dequeue()
    {
        ListNode pred = head;
        for (int i = 0; i < 0; i++) 			//找到index的前1個數字
        {
            pred = pred.next;
        }
        int value=pred.next.val;
        pred.next = pred.next.next;

        size--;
        return value;
    }

    public int size()
    {
        return size;
    }
}


class ListNode
{
    int val;
    ListNode next;
    ListNode(){}				//constructor建構元
    ListNode(int x) 			//constructor建構元
    {
        this.val=x;				//寫成val=x也可以，用來設定第6行val初始值
    }
}