using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtoBuf;
using System.IO;
using NUnit.Framework;

namespace ProtoBuf.JodaTime.Tests
{
    [TestFixture]
    public class PeopleTest
    {
        [Test]
        public void TestRead()
        {
            People people = Serializer.Deserialize<People>(
                File.OpenRead(@"..\..\jodatime-protostuff\target\people.out"));

            Console.Out.WriteLine(string.Format("People: name={0} date={1} size={2}", people.Name, people.Date, people.Size));
        }

        [Test]
        public void TestWrite()
        {
            People people = new People();
            people.Name = "Test";
            people.Date = new DateTime(2011, 11, 6, 16, 20, 0);
            people.Size = 1.3;

            Serializer.Serialize<People>(File.OpenWrite(@"..\..\jodatime-protostuff\target\people.net.out"), people);
        }
    }
}
