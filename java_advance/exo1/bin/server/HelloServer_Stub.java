// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package server;

public final class HelloServer_Stub
    extends java.rmi.server.RemoteStub
    implements hello.Hello
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_readMessage_0;
    
    static {
	try {
	    $method_readMessage_0 = hello.Hello.class.getMethod("readMessage", new java.lang.Class[] {});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public HelloServer_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of readMessage()
    public java.lang.String readMessage()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_readMessage_0, null, -4863334393441455L);
	    return ((java.lang.String) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}